package passwordValidator.dto;

import java.util.ArrayList;
import java.util.List;

public record ValidationPasswordResult(Boolean validationStatus, List<String> validationRules) {
    public ValidationPasswordResult(Boolean validationStatus) {
        this(validationStatus, new ArrayList<>());
    }
}
