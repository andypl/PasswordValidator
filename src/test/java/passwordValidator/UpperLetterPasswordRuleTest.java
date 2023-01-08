package passwordValidator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import passwordValidator.exception.UpperLetterPasswordRuleIncorrectCharacterNumberException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UpperLetterPasswordRuleTest {

    @ParameterizedTest
    @MethodSource("providerForShouldValidateRule")
    void shouldValidateRule(String value, Boolean expected) {
        PasswordRule passwordRule = new UpperLetterPasswordRule(2);
        assertEquals(expected, passwordRule.validate(value));
    }

    @Test
    void shouldThrownAnExceptionIncorrectLowLettersNumber() {
        RuntimeException exception = Assertions.assertThrows(UpperLetterPasswordRuleIncorrectCharacterNumberException.class, () -> new UpperLetterPasswordRule(0));
        assertEquals("Incorrect upper letters number!", exception.getMessage());
    }

    @Test
    public void shouldReturnRuleName() {
        PasswordRule digitRule = new DigitPasswordRule(2);
        assertEquals("Digit password rule", digitRule.getName());
    }

    private static Stream<Arguments> providerForShouldValidateRule() {
        return Stream.of(
                Arguments.of("33GGbb12", Boolean.TRUE),
                Arguments.of("Aby123", Boolean.FALSE),
                Arguments.of("Y1234gg", Boolean.FALSE),
                Arguments.of("87uhGGBdrthO", Boolean.TRUE)
        );
    }
}