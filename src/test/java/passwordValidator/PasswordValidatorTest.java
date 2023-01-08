package passwordValidator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import passwordValidator.dto.ValidationPasswordResult;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

class PasswordValidatorTest {

    @Test
    public void shouldValidatePassword() {
        //given
        int NOT_VALIDATED_RULES_SIZE = 0;
        int DIGITS = 3;
        int LOW_LETTERS = 4;
        int UPPER_LETTERS = 2;
        int MIN_LENGTH = 3;
        int MAX_LENGTH = 9;
        String password = "dA456Brkx";
        List<PasswordRule> passwordRuleList = preparePasswordRulesList(DIGITS, LOW_LETTERS, UPPER_LETTERS, MIN_LENGTH, MAX_LENGTH);

        //when
        ValidationPasswordResult passwordValidator = new PasswordValidator(password, passwordRuleList).validate();

        //then
        Assertions.assertEquals(TRUE, passwordValidator.validationStatus());
        Assertions.assertEquals(NOT_VALIDATED_RULES_SIZE, passwordValidator.validationRules().size());
    }

    @Test
    public void shouldNotValidateOneRule() {
        //given
        int NOT_VALIDATED_RULES_SIZE = 1;
        int DIGITS = 3;
        int LOW_LETTERS = 4;
        int UPPER_LETTERS = 2;
        int MIN_LENGTH = 3;
        int MAX_LENGTH = 9;
        String password = "dA456rkx";
        List<PasswordRule> passwordRuleList = preparePasswordRulesList(DIGITS, LOW_LETTERS, UPPER_LETTERS, MIN_LENGTH, MAX_LENGTH);

        //when
        ValidationPasswordResult passwordValidator = new PasswordValidator(password, passwordRuleList).validate();

        //then
        Assertions.assertEquals(FALSE, passwordValidator.validationStatus());
        Assertions.assertEquals(NOT_VALIDATED_RULES_SIZE, passwordValidator.validationRules().size());
        Assertions.assertEquals("Upper letter password rule", passwordValidator.validationRules().get(0));
    }

    @Test
    public void shouldNotValidateThreeRules() {
        //given
        int NOT_VALIDATED_RULES_SIZE = 3;
        int DIGITS = 5;
        int LOW_LETTERS = 5;
        int UPPER_LETTERS = 5;
        int MIN_LENGTH = 3;
        int MAX_LENGTH = 9;
        String password = "dA456rkx";
        List<String> notValidatedRules = List.of(
                "Low letter password rule",
                "Upper letter password rule",
                "Digit password rule"
        );
        List<PasswordRule> passwordRuleList = preparePasswordRulesList(DIGITS, LOW_LETTERS, UPPER_LETTERS, MIN_LENGTH, MAX_LENGTH);

        //when
        ValidationPasswordResult passwordValidator = new PasswordValidator(password, passwordRuleList).validate();

        //then
        Assertions.assertEquals(FALSE, passwordValidator.validationStatus());
        Assertions.assertEquals(NOT_VALIDATED_RULES_SIZE, passwordValidator.validationRules().size());
        Assertions.assertEquals(TRUE, passwordValidator.validationRules().containsAll(notValidatedRules));
    }

    private List<PasswordRule> preparePasswordRulesList(Integer digits, Integer lowLetters, Integer upperLetters) {
        return Stream.of(
                new DigitPasswordRule(digits),
                new LowLetterPasswordRule(lowLetters),
                new UpperLetterPasswordRule(upperLetters)
        ).collect(Collectors.toList());
    }

    private List<PasswordRule> preparePasswordRulesList(Integer digits, Integer lowLetters, Integer upperLetters, Integer minLength, Integer maxLength) {
        List<PasswordRule> passwordRuleList = preparePasswordRulesList(digits, lowLetters, upperLetters);
        passwordRuleList.add(new LengthPasswordRule(minLength, maxLength));

        return passwordRuleList;
    }

}