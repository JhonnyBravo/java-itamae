package java_itamae.app.validator;

import java.util.Set;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java_itamae.domain.model.Attribute;

/**
 * Attribute のバリデーションチェックを実行する。
 */
public class IsValidAttribute implements Predicate<Attribute> {
    /**
     * Attribute のバリデーションチェックを実行する。
     *
     * @param attr 判定対象とする Attribute を指定する。
     * @return isValid
     *         <ul>
     *         <li>true: エラーが無いことを表す。</li>
     *         <li>false: エラーが発生したことを表す。</li>
     *         </ul>
     */
    @Override
    public boolean test(Attribute attr) {
        final Logger logger = LoggerFactory.getLogger(this.getClass());
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final Set<ConstraintViolation<Attribute>> resultSet = validator.validate(attr);

        if (resultSet.size() > 0) {
            resultSet.stream().forEach(error -> {
                final String path = error.getPropertyPath().toString();
                final String message = error.getMessage();
                logger.warn(path + ": " + message);
            });

            return false;
        } else {
            return true;
        }

    }
}
