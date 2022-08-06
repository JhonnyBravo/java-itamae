package java_itamae.domain.model.template;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Constraint(validatedBy = {IsValidPathValidator.class})
public @interface IsValidPath {
  String message() default "{java_itamae.domain.model.template.IsValidPath}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
