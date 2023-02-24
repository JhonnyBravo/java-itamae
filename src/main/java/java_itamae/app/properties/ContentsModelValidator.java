package java_itamae.app.properties;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.function.Predicate;
import java_itamae.domain.model.contents.ContentsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** {@link ContentsModel} のバリデーションチェックを実行する。 */
@Service
public class ContentsModelValidator implements Predicate<ContentsModel> {
  /**
   * {@link ContentsModel} のバリデーションチェックを実行する。
   *
   * @param model 判定対象とする {@link ContentsModel} を指定する。
   * @return isValid
   *     <ul>
   *       <li>true: エラーが無いことを表す。
   *       <li>false: エラーが発生したことを表す。
   *     </ul>
   */
  @Override
  public boolean test(final ContentsModel model) {
    boolean result = true;

    final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    final Set<ConstraintViolation<ContentsModel>> resultSet = validator.validate(model);

    if (!resultSet.isEmpty()) {
      final Logger logger = LoggerFactory.getLogger(this.getClass());

      resultSet.stream()
          .forEach(
              error -> {
                final String path = error.getPropertyPath().toString();
                final String message = error.getMessage();
                logger.warn("{}: {}", path, message);
              });

      result = false;
    }

    return result;
  }
}
