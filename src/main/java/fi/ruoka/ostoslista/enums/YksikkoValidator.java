package fi.ruoka.ostoslista.enums;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class YksikkoValidator implements ConstraintValidator<ValidYksikko, String> {

    @Override
    public void initialize(ValidYksikko constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        for (Yksikko yksikko : Yksikko.values()) {
            if (yksikko.getYksikko().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
