package passwordValidator;

import lombok.extern.slf4j.Slf4j;
import passwordValidator.dto.PasswordValidationResult;
import passwordValidator.exception.PasswordMustNotBeNullOrEmptyExcepption;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Slf4j
public class PasswordValidator {
    private List<PasswordRule> passwordRuleList;
    List<String> rulesNotValidated = new ArrayList<>();

    public PasswordValidator(List<PasswordRule> passwordRules){
        this.passwordRuleList = passwordRules;
    }

    public PasswordValidationResult validate(String password){
        if(isEmpty(password)) {
            log.info("Password is null or empty. Provided password: {}", password);
            throw new PasswordMustNotBeNullOrEmptyExcepption(password);
        }
        validatePasswordAgainstRules(password);
        if(rulesNotValidated.isEmpty()) {
            log.info("Password validation succeeded for password: {} and rules: {}", password, passwordRuleList);
            return new PasswordValidationResult(TRUE);
        }

        log.info("Password validation failed for password: {} and rules: {}", password, rulesNotValidated);
        return new PasswordValidationResult(FALSE, rulesNotValidated);
    }

    private void validatePasswordAgainstRules(String password) {
        log.info("Starting validation for password: {} and rules: {}", password, passwordRuleList);
        passwordRuleList.stream()
                .filter(rule -> rule.validate(password).equals(FALSE))
                .forEach(rule -> rulesNotValidated.add(rule.getName()));
    }
}
