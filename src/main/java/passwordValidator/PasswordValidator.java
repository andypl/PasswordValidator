package passwordValidator;

import passwordValidator.dto.ValidationPasswordResult;

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

    public ValidationPasswordResult validate(){
        validatePasswordAgainstRules();
        if(rulesNotValidated.isEmpty()) {
            return new ValidationPasswordResult(TRUE);
        }

        return new ValidationPasswordResult(FALSE, rulesNotValidated);
    }

    private void validatePasswordAgainstRules() {
        passwordRuleList.stream()
                .filter(rule -> rule.validate(password).equals(FALSE))
                .forEach(rule -> rulesNotValidated.add(rule.getName()));
    }
}
