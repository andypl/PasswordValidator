package passwordValidator.exception;

public class IncorrectLengthPasswordRuleException extends RuntimeException {
    public IncorrectLengthPasswordRuleException(Integer minLength, Integer maxLength) {
        super(String.format("Incorrect password length! minLength: %s, maxLength: %s", minLength, maxLength));
    }
}
