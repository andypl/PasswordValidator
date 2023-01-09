package passwordValidator;

interface PasswordRule {
    String getName();

    Boolean validate(String password);
}
