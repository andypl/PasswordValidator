package passwordValidator.exception;

public class ArgumentsShouldNotBeNullException extends RuntimeException {
    public ArgumentsShouldNotBeNullException(Integer minLength, Integer maxLength) {
        super(String.format("At least one of argument is null. minLength: %s, maxLength: %s", minLength, maxLength));
    }
}
