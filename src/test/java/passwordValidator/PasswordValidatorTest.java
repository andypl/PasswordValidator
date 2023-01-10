package passwordValidator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import passwordValidator.dto.PasswordValidationResult;
import passwordValidator.exception.PasswordMustNotBeNullOrEmptyExcepption;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PasswordValidatorTest {
    private static final String DIGIT_PASSWORD_PASSWORD_RULE = "digitPasswordRule";
    private static final String LOW_LETTER_PASSWORD_RULE = "lowLetterPasswordRule";
    private static final String UPPER_LETTER_PASSWORD_RULE = "upperLetterPasswordRule";
    private static final String MIN_LENGTH_PASSWORD_RULE = "minLengthPasswordRule";
    private static final String MAX_LENGTH_PASSWORD_RULE = "maxLengthPasswordRule";
    private static final String SPECIAL_CAHRACTER_PASSWORD_RULE = "specialCharacterPasswordRule";

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
        PasswordValidationResult passwordValidationResult = new PasswordValidator(passwordRuleList).validate(password);

        //then
        assertEquals(TRUE, passwordValidationResult.validationStatus());
        assertEquals(NOT_VALIDATED_RULES_SIZE, passwordValidationResult.notValidatedRules().size());
    }

    @ParameterizedTest
    @MethodSource("providerForRuleShouldThrownAnExceptionWhenPasswordIsNullOrEmpty")
    public void shouldThrownAnExceptionWhenPasswordIsNullOrEmpty(String exceptionMessage, String password) {
        //given
        int DIGITS = 3;
        int LOW_LETTERS = 4;
        int UPPER_LETTERS = 2;
        int MIN_LENGTH = 3;
        int MAX_LENGTH = 9;
        List<PasswordRule> passwordRuleList = preparePasswordRulesList(DIGITS, LOW_LETTERS, UPPER_LETTERS, MIN_LENGTH, MAX_LENGTH);

        //when
        RuntimeException excepion = Assertions.assertThrows(PasswordMustNotBeNullOrEmptyExcepption.class, () -> new PasswordValidator(passwordRuleList).validate(password));

        //then
        assertEquals(exceptionMessage, excepion.getMessage());
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
        PasswordValidationResult passwordValidationResult = new PasswordValidator(passwordRuleList).validate(password);

        //then
        assertEquals(FALSE, passwordValidationResult.validationStatus());
        assertEquals(NOT_VALIDATED_RULES_SIZE, passwordValidationResult.notValidatedRules().size());
        assertEquals("Upper letter password rule", passwordValidationResult.notValidatedRules().get(0));
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
        List<String> notValidatedRules = Stream.of(
                "Low letter password rule",
                "Upper letter password rule",
                "Digit password rule"
        ).collect(Collectors.toCollection(ArrayList::new));
        List<PasswordRule> passwordRuleList = preparePasswordRulesList(DIGITS, LOW_LETTERS, UPPER_LETTERS, MIN_LENGTH, MAX_LENGTH);

        //when
        PasswordValidationResult passwordValidationResult = new PasswordValidator(passwordRuleList).validate(password);

        //then
        assertEquals(FALSE, passwordValidationResult.validationStatus());
        assertEquals(NOT_VALIDATED_RULES_SIZE, passwordValidationResult.notValidatedRules().size());
        assertEquals(TRUE, passwordValidationResult.notValidatedRules().containsAll(notValidatedRules));
    }

    @Test
    public void shouldNotValidateAllRules() {
        //given
        String password = "3uyUBH%";
        Map<String, Integer> rulesParams = Map.of(
                DIGIT_PASSWORD_PASSWORD_RULE, 2,
                LOW_LETTER_PASSWORD_RULE, 3,
                UPPER_LETTER_PASSWORD_RULE, 4,
                MIN_LENGTH_PASSWORD_RULE, 1,
                MAX_LENGTH_PASSWORD_RULE, 3,
                SPECIAL_CAHRACTER_PASSWORD_RULE, 2
        );
        List<String> notValidatedRules = Stream.of(
                "Low letter password rule",
                "Upper letter password rule",
                "Digit password rule",
                "Length password rule",
                "Special character password rule"
        ).collect(Collectors.toCollection(ArrayList::new));
        List<PasswordRule> passwordRuleList = preparePasswordRulesList(rulesParams);

        //when
        PasswordValidationResult passwordValidatorResult = new PasswordValidator(passwordRuleList).validate(password);

        //then
        assertEquals(FALSE, passwordValidatorResult.validationStatus());
        assertEquals(TRUE, passwordValidatorResult.notValidatedRules().containsAll(notValidatedRules));
    }

    private List<PasswordRule> preparePasswordRulesList(Integer digits, Integer lowLetters, Integer upperLetters) {
        return Stream.of(
                new DigitPasswordRule(digits),
                new LowLetterPasswordRule(lowLetters),
                new UpperLetterPasswordRule(upperLetters)
        ).collect(Collectors.toCollection(ArrayList::new));
    }

    private List<PasswordRule> preparePasswordRulesList(Map<String, Integer> rulesParams) {
        return Stream.of(
                new DigitPasswordRule(rulesParams.get(DIGIT_PASSWORD_PASSWORD_RULE)),
                new LowLetterPasswordRule(rulesParams.get(LOW_LETTER_PASSWORD_RULE)),
                new UpperLetterPasswordRule(rulesParams.get(UPPER_LETTER_PASSWORD_RULE)),
                new LengthPasswordRule(rulesParams.get(MIN_LENGTH_PASSWORD_RULE), rulesParams.get(MAX_LENGTH_PASSWORD_RULE)),
                new SpecialCharacterPasswordRule(rulesParams.get(SPECIAL_CAHRACTER_PASSWORD_RULE))
        ).collect(Collectors.toCollection(ArrayList::new));
    }

    private List<PasswordRule> preparePasswordRulesList(Integer digits, Integer lowLetters, Integer upperLetters, Integer minLength, Integer maxLength) {
        List<PasswordRule> passwordRuleList = preparePasswordRulesList(digits, lowLetters, upperLetters);
        passwordRuleList.add(new LengthPasswordRule(minLength, maxLength));

        return passwordRuleList;
    }

    public static Stream<Arguments> providerForRuleShouldThrownAnExceptionWhenPasswordIsNullOrEmpty() {
        return Stream.of(
                Arguments.of("Password must not be empty or null! Entered password: null", null),
                Arguments.of("Password must not be empty or null! Entered password: ", "")
        );
    }

}