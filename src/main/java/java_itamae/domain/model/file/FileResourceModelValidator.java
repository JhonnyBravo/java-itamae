package java_itamae.domain.model.file;

import java.util.Set;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

/** {@link FileResourceModel} のバリデーションチェックを実行する。 */
public class FileResourceModelValidator implements Predicate<FileResourceModel> {
  /**
   * {@link FileResourceModel} のバリデーションチェックを実行する。
   *
   * @param model 判定対象とする {@link FileResourceModel} を指定する。
   * @return isValid
   *     <ul>
   *       <li>true: エラーが無いことを表す。
   *       <li>false: エラーが発生したことを表す。
   *     </ul>
   */
  @Override
  public boolean test(FileResourceModel model) {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    final Set<ConstraintViolation<FileResourceModel>> resultSet = validator.validate(model);

    if (resultSet.isEmpty()) {
      return true;
    }
    resultSet.stream()
        .forEach(
            error -> {
              final String path = error.getPropertyPath().toString();
              final String message = error.getMessage();
              logger.warn("{}: {}", path, message);
            });

    return false;
  }
}
