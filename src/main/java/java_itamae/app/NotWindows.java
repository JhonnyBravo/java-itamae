package java_itamae.app;

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
@Constraint(validatedBy = { OsValidator.class })
public @interface NotWindows {
    String message() default "{java_itamae.app.NotWindows.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
