package passwordValidator;

import java.util.ArrayList;
import java.util.List;

public record ValidationResult(Boolean validationStatus, List<String> validationRules) {
    public ValidationResult(Boolean validationStatus) {
        this(validationStatus, new ArrayList<>());
    }

    public ValidationResult(Boolean validationStatus, List<String> validationRules) {
        this.validationStatus = validationStatus;
        this.validationRules = validationRules;
    }
}
