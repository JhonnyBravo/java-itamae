package java_itamae_contents.domain.model.template;

import java.util.function.Predicate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CreateValidator implements ConstraintValidator<Create, TemplateResourceModel> {
  @Override
  public void initialize(Create annotation) {}

  private final Predicate<String> isNotNull =
      value -> {
        boolean result = false;

        if (value != null && !value.isEmpty()) {
          result = true;
        }

        return result;
      };

  @Override
  public boolean isValid(TemplateResourceModel model, ConstraintValidatorContext context) {
    boolean result = true;
    final String action = model.getAction();

    if (action.equals("create")
        && (!isNotNull.test(model.getPath()) || !isNotNull.test(model.getSource()))) {
      result = false;
    }

    return result;
  }
}
