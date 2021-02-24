package java_itamae.app.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotWindowsValidator implements ConstraintValidator<NotWindows, String> {

    @Override
    public void initialize(NotWindows constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = true;

        final String osName = System.getProperty("os.name");

        if (value != null && osName.substring(0, 3).equals("Win")) {
            result = false;
        }

        return result;
    }
}
