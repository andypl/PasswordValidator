package passwordValidator.exception;

public class IncorrectLengthPasswordRuleException extends RuntimeException {
    public IncorrectLengthPasswordRuleException(String message) {
        super(message);
    }
}
