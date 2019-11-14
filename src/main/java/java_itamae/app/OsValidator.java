package java_itamae.app;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java_itamae.domain.model.Attribute;

public class OsValidator implements ConstraintValidator<NotWindows, Attribute> {

    @Override
    public void initialize(NotWindows constraintAnnotation) {
    }

    @Override
    public boolean isValid(Attribute attr, ConstraintValidatorContext context) {
        boolean result = true;

        final String osName = System.getProperty("os.name");

        if (attr.getMode() != null && osName.substring(0, 3).equals("Win")) {
            result = false;
        }

        if (attr.getGroup() != null && osName.substring(0, 3).equals("Win")) {
            result = false;
        }

        return result;
    }
}
