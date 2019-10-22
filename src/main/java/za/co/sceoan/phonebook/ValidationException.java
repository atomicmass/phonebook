package za.co.sceoan.phonebook;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;

public class ValidationException extends Exception {
    private final Set<String> failedValidations = new HashSet<>();
    
    public ValidationException(Set<? extends ConstraintViolation<?>> violations) {
        failedValidations.addAll(violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet()));
    }
    
    public ValidationException(String violation) {
        failedValidations.add(violation);
    }

    public Set<String> getFailedValidations() {
        return failedValidations;
    }
    
    
}
