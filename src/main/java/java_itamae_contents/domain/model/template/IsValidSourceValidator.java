package java_itamae_contents.domain.model.template;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.io.File;
import java.util.function.Predicate;

public class IsValidSourceValidator
    implements ConstraintValidator<IsValidSource, TemplateResourceModel> {
  @Override
  public void initialize(IsValidSource annotation) {}

  private final Predicate<String> isNull =
      value -> {
        boolean result = false;

        if (value == null || value.isEmpty()) {
          result = true;
        }

        return result;
      };

  @Override
  public boolean isValid(TemplateResourceModel model, ConstraintValidatorContext context) {
    boolean result = true;
    final String action = model.getAction();
    final String source = model.getSource();

    if (action != null && action.equals("create")) {
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
