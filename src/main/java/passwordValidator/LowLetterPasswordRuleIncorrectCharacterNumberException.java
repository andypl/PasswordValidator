package passwordValidator;

public class LowLetterPasswordRuleIncorrectCharacterNumberException extends RuntimeException {
    public LowLetterPasswordRuleIncorrectCharacterNumberException(String message) {
        super(message);
    }
}
