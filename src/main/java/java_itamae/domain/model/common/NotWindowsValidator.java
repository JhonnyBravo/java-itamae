package java_itamae.domain.model.common;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/** OS が Windows ではないことを確認する。 */
public class NotWindowsValidator implements ConstraintValidator<NotWindows, String> {

  @Override
  public void initialize(NotWindows constraintAnnotation) {}

  /**
   * システムプロパティを読込み、 OS が Windows ではないことを確認する。
   *
   * @param value 確認対象とする文字列を指定する。
   * @param context {@link ConstraintValidatorContext} を指定する。
   * @return result
   *     <ul>
   *       <li>true: Windows ではないことを表す。
   *       <li>false: Windows であることを表す。
   *     </ul>
   */
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
