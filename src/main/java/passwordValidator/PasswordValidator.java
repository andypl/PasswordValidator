package passwordValidator;

import lombok.extern.slf4j.Slf4j;
import passwordValidator.dto.ValidationPasswordResult;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Slf4j
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

        log.info("Password validation failed for password: " + password + " and rules: " + rulesNotValidated);
        return new ValidationPasswordResult(FALSE, rulesNotValidated);
    }

    private void validatePasswordAgainstRules() {
        log.info("Starting validation for password: " + password + " and rules: " + passwordRuleList);
        passwordRuleList.stream()
                .filter(rule -> rule.validate(password).equals(FALSE))
                .forEach(rule -> rulesNotValidated.add(rule.getName()));
    }
}
