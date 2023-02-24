package java_itamae.domain.model.common;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** OS が Windows ではないことを確認する。 */
@Documented
@Retention(RUNTIME)
@Target({
  ElementType.METHOD,
  ElementType.FIELD,
  ElementType.ANNOTATION_TYPE,
  ElementType.CONSTRUCTOR,
  ElementType.PARAMETER,
  ElementType.TYPE_USE
})
@Constraint(validatedBy = {NotWindowsValidator.class})
public @interface NotWindows {
  String message() default "{java_itamae.domain.model.file.NotWindows}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  @Target({
    ElementType.METHOD,
    ElementType.FIELD,
    ElementType.ANNOTATION_TYPE,
    ElementType.CONSTRUCTOR,
    ElementType.PARAMETER,
    ElementType.TYPE_USE
  })
  @Retention(RUNTIME)
  @Documented
  @interface List {
    NotWindows[] value();
  }
}
