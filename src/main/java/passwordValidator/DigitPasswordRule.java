package passwordValidator;

import passwordValidator.exception.DigitPasswordRuleIncorrectCharacterNumberException;

import java.util.regex.Pattern;

import static java.lang.Character.isDigit;

class DigitPasswordRule implements PasswordRule{

    public static final String DIGIT_REGEX = ".*[0-9].*";
    public static final String DIGIT_PASSWORD_RULE_NAME = "Digit password rule";
    private final Integer digitLengths;
    public DigitPasswordRule(Integer digitLengths) {
        if(digitLengths <= 0) {
            throw new DigitPasswordRuleIncorrectCharacterNumberException("Incorrect digits number!");
        }
        this.digitLengths = digitLengths;
    }

    @Override
    public String getName() {
        return DIGIT_PASSWORD_RULE_NAME;
    }

    @Override
    public Boolean validate(String password) {
        Pattern digitPattern = Pattern.compile(DIGIT_REGEX);
        if(password.length() < digitLengths) {
            return false;
        } else if (!digitPattern.matcher(password).matches()) {
            return false;
        }

        return countDigitInPassword(password) >= digitLengths;
    }

    private Integer countDigitInPassword(String password) {
        Integer digitCharacterSum = 0;
        for (int i = 0; i < password.length(); i++) {
            char passwordCharacter = password.charAt(i);
            if(isDigit(passwordCharacter)) {
                digitCharacterSum++;
            }
        }
        return digitCharacterSum;
    }
}