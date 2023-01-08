package passwordValidator;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class PasswordValidator {
    private String password;
    private List<PasswordRule> passwordRuleList;
    List<String> rulesNotValidated = new ArrayList<>();

    public PasswordValidator(String password, List<PasswordRule> passwordRules) {
        this.password = password;
        this.passwordRuleList = passwordRules;
    }

    public void validate(){
        passwordRuleList.stream()
                .filter(rule -> rule.validate(password).equals(FALSE))
                .forEach(rule -> rulesNotValidated.add(rule.getName()));
    }

    public ValidationResult getResult() {
        if(rulesNotValidated.isEmpty()) {
           return new ValidationResult(TRUE);
        }
        return new ValidationResult(FALSE, rulesNotValidated);
    }
}
