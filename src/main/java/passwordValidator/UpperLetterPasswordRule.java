package passwordValidator;

import java.util.regex.Pattern;

import static java.lang.Character.isUpperCase;

public class UpperLetterPasswordRule implements PasswordRule{
    public static final String LOW_LETTER_REGEX = ".*[A-Z].*";
    private final Integer upperLetterLengths;
    public UpperLetterPasswordRule(Integer upperLetterLengths) {
        if(upperLetterLengths <= 0) {
            throw new RuntimeException("Incorrect upper letters number!");
        }
        this.upperLetterLengths = upperLetterLengths;
    }

    @Override
    public String getName() {
        return "Upper letter password rule";
    }

    @Override
    public Boolean validate(String password) {
        Pattern upperLetterPattern = Pattern.compile(LOW_LETTER_REGEX);
        if(password.length() < upperLetterLengths) {
            return false;
        } else if (!upperLetterPattern.matcher(password).matches()) {
            return false;
        }

        return countUpperLettersInPassword(password) >= upperLetterLengths;
    }

    private Integer countUpperLettersInPassword(String password) {
        Integer upperLetterCharacterSum = 0;
        for (int i = 0; i < password.length(); i++) {
            char passwordCharacter = password.charAt(i);

            if(isUpperCase(passwordCharacter)) {
                upperLetterCharacterSum++;
            }
        }
        return upperLetterCharacterSum;
    }
}
