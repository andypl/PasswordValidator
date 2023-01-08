package passwordValidator;

public interface PasswordRule {
    String getName();
    Boolean validate(String password);
}
