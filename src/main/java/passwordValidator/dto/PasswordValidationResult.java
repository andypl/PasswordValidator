package passwordValidator.dto;

import java.util.ArrayList;
import java.util.List;

public record PasswordValidationResult(Boolean validationStatus, List<String> notValidatedRules) {
    public PasswordValidationResult(Boolean validationStatus) {
        this(validationStatus, new ArrayList<>());
    }
}
