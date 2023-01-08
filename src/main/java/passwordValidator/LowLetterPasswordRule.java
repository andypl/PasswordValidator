package passwordValidator;

import java.util.regex.Pattern;

import static java.lang.Character.isLowerCase;

public class LowLetterPasswordRule implements PasswordRule{
    public static final String LOW_LETTER_REGEX = ".*[a-z].*";
    private final Integer lowLetterLengths;

    public LowLetterPasswordRule(Integer lowLettersLengths) {
        if(lowLettersLengths <= 0) {
            throw new RuntimeException("Incorrect low letters number!");
        }
        this.lowLetterLengths = lowLettersLengths;
    }

    @Override
    public String getName() {
        return "Low letter password rule";
    }

    @Override
    public Boolean validate(String password) {
        Pattern lowLetterPattern = Pattern.compile(LOW_LETTER_REGEX);
        if(password.length() < lowLetterLengths) {
            return false;
        } else if (!lowLetterPattern.matcher(password).matches()) {
            return false;
        }

        return countLowLettersInPassword(password) >= lowLetterLengths;
    }

    private Integer countLowLettersInPassword(String password) {
        Integer lowLetterCharacterSum = 0;
        for (int i = 0; i < password.length(); i++) {
            char passwordCharacter = password.charAt(i);

            if(isLowerCase(passwordCharacter)) {
                lowLetterCharacterSum++;
            }
        }
        return lowLetterCharacterSum;
    }
}
