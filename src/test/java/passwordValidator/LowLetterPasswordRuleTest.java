package passwordValidator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LowLetterPasswordRuleTest {

    @ParameterizedTest
    @MethodSource("providerForShouldValidateRule")
    void shouldValidateRule(String value, Boolean expected) {
        PasswordRule passwordRule = new LowLetterPasswordRule(2);
        assertEquals(expected, passwordRule.validate(value));
    }

    @Test
    void shouldThrownAnExceptionIncorrectLowLettersNumber() {
        RuntimeException exception = Assertions.assertThrows(LowLetterPasswordRuleIncorrectCharacterNumberException.class, () -> new LowLetterPasswordRule(0));
        assertEquals("Incorrect low letters number!", exception.getMessage());
    }

    @Test
    public void shouldReturnRuleName() {
        PasswordRule lowLetterRule = new LowLetterPasswordRule(2);
        assertEquals("Low letter password rule", lowLetterRule.getName());
    }

    private static Stream<Arguments> providerForShouldValidateRule() {
        return Stream.of(
                Arguments.of("12bXa", Boolean.TRUE),
                Arguments.of("AAb123", Boolean.FALSE),
                Arguments.of("Y1234GG", Boolean.FALSE),
                Arguments.of("87UuhGGBd", Boolean.TRUE)
        );
    }
}