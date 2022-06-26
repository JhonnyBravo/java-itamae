package java_itamae_contents.domain.model.template;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.io.File;
import java.util.function.Predicate;

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

  @Override
  public boolean isValid(TemplateResourceModel model, ConstraintValidatorContext context) {
    boolean result = true;
    final String action = model.getAction();
    final String path = model.getPath();

    if (action.equals("create")) {
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
