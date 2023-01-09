package passwordValidator;

import lombok.extern.slf4j.Slf4j;
import passwordValidator.dto.ValidationPasswordResult;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Slf4j
public class PasswordValidator {
    private List<PasswordRule> passwordRuleList;
    List<String> rulesNotValidated = new ArrayList<>();

    public PasswordValidator(List<PasswordRule> passwordRules) {
        this.passwordRuleList = passwordRules;
    }

    public ValidationPasswordResult validate(String password){
        validatePasswordAgainstRules(password);
        if(rulesNotValidated.isEmpty()) {
            log.info("Password validation succeeded for password: {}", password);
            return new ValidationPasswordResult(TRUE);
        }

        log.info("Password validation failed for password: {} and rules: {}", password, rulesNotValidated);
        return new ValidationPasswordResult(FALSE, rulesNotValidated);
    }

    private void validatePasswordAgainstRules(String password) {
        log.info("Starting validation for password: {} and rules: {}", password, passwordRuleList);
        passwordRuleList.stream()
                .filter(rule -> rule.validate(password).equals(FALSE))
                .forEach(rule -> rulesNotValidated.add(rule.getName()));
    }
}
