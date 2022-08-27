package java_itamae.domain.model.template;

import java.io.File;
import java.util.function.Predicate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/** {@link TemplateResourceModel} の action の値として create が指定されている場合に複合チェックを実行する。 */
public class IsValidPathValidator
    implements ConstraintValidator<IsValidPath, TemplateResourceModel> {
  @Override
  public void initialize(IsValidPath annotation) {}

  private final Predicate<String> isNull =
      value -> {
        boolean result = false;

        if (value == null || value.isEmpty()) {
          result = true;
        }

        return result;
      };

  /**
   * {@link TemplateResourceModel} の action の値として create が指定されている場合に以下の複合チェックを実行する。
   *
   * <ul>
   *   <li>action の値が create であること。
   *   <li>かつ path の値が null または 空文字ではないこと。
   *   <li>かつ path に指定されたファイルが既に存在していること。
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
  public boolean isValid(TemplateResourceModel model, ConstraintValidatorContext context) {
    boolean result = true;
    final String action = model.getAction();
    final String path = model.getPath();

    if (action != null && action.equals("create")) {
      // action = create かつ path が指定されていない場合
      if (isNull.test(path)) {
        result = false;
      } else {
        // action = create かつ path に指定されたファイルが存在しない場合
        final File file = new File(path);

        if (!file.isFile()) {
          result = false;
        }
      }
    }

    return result;
  }
}
