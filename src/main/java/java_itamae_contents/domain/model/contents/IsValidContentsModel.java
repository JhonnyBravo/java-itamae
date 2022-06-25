package java_itamae_contents.domain.model.contents;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** {@link ContentsModel} のバリデーションチェックを実行する。 */
public class IsValidContentsModel implements Predicate<ContentsModel> {
  /**
   * {@link ContentsModel} のバリデーションチェックを実行する。
   *
   * @param attr 判定対象とする {@link ContentsModel} を指定する。
   * @return isValid
   *     <ul>
   *       <li>true: エラーが無いことを表す。
   *       <li>false: エラーが発生したことを表す。
   *     </ul>
   */
  @Override
  public boolean test(ContentsModel attr) {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    final Set<ConstraintViolation<ContentsModel>> resultSet = validator.validate(attr);

    if (resultSet.size() > 0) {
      resultSet.stream()
          .forEach(
              error -> {
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
