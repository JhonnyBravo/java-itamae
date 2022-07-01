package java_itamae.domain.model.template;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Constraint(validatedBy = {IsValidSourceValidator.class})
public @interface IsValidSource {
  String message() default "{java_itamae_contents.domain.model.template.IsValidSource}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
