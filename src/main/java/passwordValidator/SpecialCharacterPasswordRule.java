package passwordValidator;

import org.apache.commons.lang3.StringUtils;
import passwordValidator.exception.SpecialLetterPasswordRuleIncorrectCharacterNumberException;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class SpecialCharacterPasswordRule implements PasswordRule{
    private static final String SPECIAL_CHARACTERS = ".*[!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~].*";
    private static final String SPECIAL_CHARACTER_REGEX = ".*[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~].*";
    private static final String PASSWORD_RULE_NAME = "Special character password rule";
    private final Integer specialCharactersLengths;

    public SpecialCharacterPasswordRule(Integer specialCharactersLengths) {
        if (isNull(specialCharactersLengths) || (specialCharactersLengths <= 0)) {
            throw new SpecialLetterPasswordRuleIncorrectCharacterNumberException(specialCharactersLengths);
        }
        this.specialCharactersLengths = specialCharactersLengths;
    }
    @Override
    public String getName() {
        return PASSWORD_RULE_NAME;
    }

    @Override
    public Boolean validate(String password) {
        Pattern specialCharactersPattern = Pattern.compile(SPECIAL_CHARACTER_REGEX);
        if (password.length() < specialCharactersLengths) {
            return false;
        } else if (!specialCharactersPattern.matcher(password).matches()) {
            return false;
        }

        return countSpecialCharactersInPassword(password) >= specialCharactersLengths;
    }

    private Integer countSpecialCharactersInPassword(String password) {
        Integer upperLetterCharacterSum = 0;
        for (int i = 0; i < password.length(); i++) {
            char passwordCharacter = password.charAt(i);

            if (isSpecialCharacter(passwordCharacter)) {
                upperLetterCharacterSum++;
            }
        }
        return upperLetterCharacterSum;
    }

    private Boolean isSpecialCharacter(char passwordCharacter) {
        return StringUtils.contains(SPECIAL_CHARACTERS, passwordCharacter);
    }
}
