package passwordValidator;

import passwordValidator.exception.LowLetterPasswordRuleIncorrectCharacterNumberException;

import java.util.regex.Pattern;

import static java.lang.Character.isLowerCase;

class LowLetterPasswordRule implements PasswordRule{
    public static final String LOW_LETTER_REGEX = ".*[a-z].*";
    public static final String LOW_LETTER_PASSWORD_RULE_NAME = "Low letter password rule";
    private final Integer lowLetterLengths;

    public LowLetterPasswordRule(Integer lowLettersLengths) {
        if(lowLettersLengths <= 0) {
            throw new LowLetterPasswordRuleIncorrectCharacterNumberException("Incorrect low letters number!");
        }
        this.lowLetterLengths = lowLettersLengths;
    }

    @Override
    public String getName() {
        return LOW_LETTER_PASSWORD_RULE_NAME;
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
