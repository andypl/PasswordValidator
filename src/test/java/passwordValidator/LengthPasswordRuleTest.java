package passwordValidator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import passwordValidator.exception.ArgumentsShouldNotBeNullException;
import passwordValidator.exception.IncorrectLengthPasswordRuleException;

import java.util.stream.Stream;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LengthPasswordRuleTest {

    @ParameterizedTest
    @MethodSource("providerForRuleShouldValidateRule")
    public void shouldValidateRule(Boolean validationStatus, String password, Integer minLength, Integer maxLength) {
        Boolean validationResult = new LengthPasswordRule(minLength, maxLength).validate(password);
        Assertions.assertEquals(validationStatus, validationResult);
    }

    @ParameterizedTest
    @MethodSource("providerForRuleShouldNotValidateRule")
    public void shouldNotValidateRule(Boolean validationStatus, String password, Integer minLength, Integer maxLength) {
        Boolean validationResult = new LengthPasswordRule(minLength, maxLength).validate(password);
        Assertions.assertEquals(validationStatus, validationResult);
    }

    @ParameterizedTest
    @MethodSource("providerForRuleShouldThrownAnExceptionWhenArgumentIsNull")
    public void shouldThrownAnExceptionWhenMinLengthArgumentIsNull(String exceptionMessage, Integer minLlength, Integer maxLength) {
        RuntimeException exception = Assertions.assertThrows(ArgumentsShouldNotBeNullException.class, () -> new LengthPasswordRule(minLlength, maxLength));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("providerForShouldThrownAnExceptionWhenPasswordLengthIsIncorrect")
    public void shouldThrownAnExceptionWhenPasswordLengthIsIncorrect(String exceptionMessage, Integer minLength, Integer maxLength) {
        RuntimeException exception = Assertions.assertThrows(IncorrectLengthPasswordRuleException.class, () -> new LengthPasswordRule(minLength, maxLength));

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    public void shouldThrownAnExceptionWhenAllArgumentIsNull() {
        RuntimeException exception = Assertions.assertThrows(ArgumentsShouldNotBeNullException.class, () -> new LengthPasswordRule(null, null));

        assertEquals("At least one of argument is null. minLength: null, maxLength: null", exception.getMessage());
    }

    @Test
    public void shouldReturnRuleName() {
        PasswordRule lowLetterRule = new LengthPasswordRule(5, 10);
        assertEquals("Length password rule", lowLetterRule.getName());
    }

    public static Stream<Arguments> providerForRuleShouldValidateRule() {
        return Stream.of(
                Arguments.of(TRUE, "abc", 3, 8),
                Arguments.of(TRUE, "abc12", 5, 5),
                Arguments.of(TRUE, "abc12", 1, 5)
        );
    }

    public static Stream<Arguments> providerForRuleShouldNotValidateRule() {
        return Stream.of(
                Arguments.of(FALSE, "abc", 4, 8),
                Arguments.of(FALSE, "abc125", 5, 5),
                Arguments.of(FALSE, "abc12gb", 1, 5)
        );
    }

    public static Stream<Arguments> providerForShouldThrownAnExceptionWhenPasswordLengthIsIncorrect() {
        return Stream.of(
                Arguments.of("Incorrect password length! minLength: 0, maxLength: 0", 0, 0),
                Arguments.of("Incorrect password length! minLength: 0, maxLength: 1", 0, 1),
                Arguments.of("Incorrect password length! minLength: 1, maxLength: 0", 1, 0),
                Arguments.of("Incorrect password length! minLength: 7, maxLength: 5", 7, 5)
        );
    }

    public static Stream<Arguments> providerForRuleShouldThrownAnExceptionWhenArgumentIsNull() {
        return Stream.of(
                Arguments.of("At least one of argument is null. minLength: null, maxLength: 5", null, 5),
                Arguments.of("At least one of argument is null. minLength: 5, maxLength: null", 5, null),
                Arguments.of("At least one of argument is null. minLength: null, maxLength: null", null, null)
        );
    }
}