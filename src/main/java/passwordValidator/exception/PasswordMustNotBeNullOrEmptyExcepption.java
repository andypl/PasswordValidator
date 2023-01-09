package passwordValidator.exception;

public class PasswordMustNotBeNullOrEmptyExcepption extends RuntimeException{
    public PasswordMustNotBeNullOrEmptyExcepption(String password) {
        super(String.format("Password must not be empty or null! Entered password: %s", password));
    }
}
