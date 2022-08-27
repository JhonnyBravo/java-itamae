package java_itamae.domain.model.template;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/** {@link TemplateResourceModel} の action の値として create が指定されている場合に複合チェックを実行する。 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Constraint(validatedBy = {IsValidSourceValidator.class})
public @interface IsValidSource {
  String message() default "{java_itamae.domain.model.template.IsValidSource}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
