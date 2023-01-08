package passwordValidator;

public class DigitPasswordRuleIncorrectCharacterNumberException extends RuntimeException {
    public DigitPasswordRuleIncorrectCharacterNumberException(String message) {
        super(message);
    }
}
