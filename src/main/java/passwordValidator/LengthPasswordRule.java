package passwordValidator;

import passwordValidator.exception.ArgumentsShouldNotBeNullException;
import passwordValidator.exception.IncorrectLengthPasswordRuleException;

import static java.util.Objects.isNull;

class LengthPasswordRule implements PasswordRule{
    public static final String PASSWORD_RULE_NAME = "Length password rule";
    private final Integer minLength;
    private final Integer maxLength;
    public LengthPasswordRule(Integer minLength, Integer maxLength) {
        if(isNull(minLength) || isNull(maxLength)) {
            throw new ArgumentsShouldNotBeNullException(minLength, maxLength);
        }
        if((minLength <= 0 || maxLength <= 0) || (minLength > maxLength)) {
            throw new IncorrectLengthPasswordRuleException(minLength, maxLength);
        }
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public String getName() {
        return PASSWORD_RULE_NAME;
    }

    @Override
    public Boolean validate(String password) {
        int length = password.length();
        if(length < minLength || length > maxLength) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }
}
