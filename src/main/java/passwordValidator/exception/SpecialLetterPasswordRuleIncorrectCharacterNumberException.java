package passwordValidator.exception;

public class SpecialLetterPasswordRuleIncorrectCharacterNumberException extends RuntimeException{
    public SpecialLetterPasswordRuleIncorrectCharacterNumberException(Integer characterLengths) {
        super(String.format("Incorrect special characters number! Special character lengths: %s", characterLengths));
    }
}
