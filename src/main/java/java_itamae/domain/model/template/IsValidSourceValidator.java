package java_itamae.domain.model.template;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.io.File;
import java.util.function.Predicate;

/** {@link TemplateResourceModel} の action の値として create が指定されている場合に複合チェックを実行する。 */
public class IsValidSourceValidator
    implements ConstraintValidator<IsValidSource, TemplateResourceModel> {

  /** value に指定した文字列が null である場合、または空文字である場合に true を返す。条件に該当しない場合は false を返す。 */
  private final transient Predicate<String> isNull =
      value -> {
        boolean result = false;

        if (value == null || value.isEmpty()) {
          result = true;
        }

        return result;
      };

  @Override
  public void initialize(final IsValidSource annotation) {}

  /**
   * {@link TemplateResourceModel} の action の値として create が指定されている場合に以下の複合チェックを実行する。
   *
   * <ul>
   *   <li>action の値が create であること。
   *   <li>かつ source の値が null または 空文字ではないこと。
   *   <li>かつ source に指定されたファイルが既に存在していること。
   * </ul>
   *
   * @param model 確認対象とする {@link TemplateResourceModel} を指定する。
   * @param context {@link ConstraintValidatorContext} を指定する。
   * @return result
   *     <ul>
   *       <li>true: 複合チェックの条件を全て満たしていることを表す。
   *       <li>false: 複合チェックのいずれかの条件を満たしていないことを表す。
   *     </ul>
   */
  @Override
  public boolean isValid(
      final TemplateResourceModel model, final ConstraintValidatorContext context) {
    boolean result = true;
    final String action = model.getAction();
    final String source = model.getSource();

    if ("create".equals(action)) {
      // action = create かつ source が指定されていない場合
      if (isNull.test(source)) {
        result = false;
      } else {
        // action = create かつ source に指定されたファイルが存在しない場合
        final File file = new File(source);

        if (!file.isFile()) {
          result = false;
        }
      }
    }

    return result;
  }
}
