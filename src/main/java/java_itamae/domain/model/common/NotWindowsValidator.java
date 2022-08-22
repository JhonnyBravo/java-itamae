package java_itamae.domain.model.common;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/** OS が Windows であるかどうかを確認する。 */
public class NotWindowsValidator implements ConstraintValidator<NotWindows, String> {

  @Override
  public void initialize(NotWindows constraintAnnotation) {}

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
