package passwordValidator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DigitPasswordRuleTest {

    @ParameterizedTest
    @MethodSource("providerForShouldValidateRule")
    void shouldValidateRule(String value, Boolean expected) {
        PasswordRule passwordRule = new DigitPasswordRule(2);
        assertEquals(expected, passwordRule.validate(value));
    }

    @Test
    void shouldThrownAnExceptionIncorrectLowLettersNumber() {
        RuntimeException exception = Assertions.assertThrows(DigitPasswordRuleIncorrectCharacterNumberException.class, () -> new DigitPasswordRule(0));
        assertEquals("Incorrect digits number!", exception.getMessage());
    }

    private static Stream<Arguments> providerForShouldValidateRule() {
        return Stream.of(
                Arguments.of("12bXa", Boolean.TRUE),
                Arguments.of("AAb1agj", Boolean.FALSE),
                Arguments.of("YUuiFGG", Boolean.FALSE),
                Arguments.of("87UuhG4Bd45", Boolean.TRUE)
        );
    }
}