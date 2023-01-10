package passwordValidator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import passwordValidator.exception.SpecialLetterPasswordRuleIncorrectCharacterNumberException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpecialCharacterPasswordRuleTest {

    @ParameterizedTest
    @MethodSource("providerForShouldValidateRule")
    void shouldValidateRule(String value, Boolean expected) {
        PasswordRule passwordRule = new SpecialCharacterPasswordRule(2);
        assertEquals(expected, passwordRule.validate(value));
    }

    @ParameterizedTest
    @MethodSource("providerForRuleShouldThrownAnExceptionSpecialLetterPasswordRuleIncorrectCharacterNumber")
    void shouldThrownAnExceptionSpecialLetterPasswordRuleIncorrectCharacterNumber(String exceptionMessage, Integer specialCharacterLengths) {
        RuntimeException exception = Assertions.assertThrows(SpecialLetterPasswordRuleIncorrectCharacterNumberException.class, () -> new SpecialCharacterPasswordRule(specialCharacterLengths));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    public void shouldReturnRuleName() {
        PasswordRule lowLetterRule = new SpecialCharacterPasswordRule(2);
        assertEquals("Special character password rule", lowLetterRule.getName());
    }

    private static Stream<Arguments> providerForShouldValidateRule() {
        return Stream.of(
                Arguments.of("12b:X{a", Boolean.TRUE),
                Arguments.of("AAb123", Boolean.FALSE),
                Arguments.of("Y1234GG", Boolean.FALSE),
                Arguments.of("87Uuh*GGB|d", Boolean.TRUE),
                Arguments.of("A", Boolean.FALSE),
                Arguments.of("A*", Boolean.FALSE)
        );
    }

    private static Stream<Arguments> providerForRuleShouldThrownAnExceptionSpecialLetterPasswordRuleIncorrectCharacterNumber() {
        return Stream.of(
                Arguments.of("Incorrect special characters number! Special character lengths: null", null),
                Arguments.of("Incorrect special characters number! Special character lengths: 0", 0)
        );
    }

}