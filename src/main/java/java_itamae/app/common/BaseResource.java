package java_itamae.app.common;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Map;
import java.util.Set;
import java_itamae.domain.model.status.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Resource クラスの土台となるインターフェイス */
public interface BaseResource<T> {
  /**
   * {@link Logger} を生成して返す。
   *
   * @return logger {@link Logger}
   */
  default Logger getLogger() {
    return LoggerFactory.getLogger(this.getClass());
  }

  /**
   * プロパティ群を収めた {@link Map} を Model クラスに変換して返す。
   *
   * @param properties プロパティ群を収めた {@link Map} を指定する。
   * @return model {@link Map} から変換された Model クラスを返す。
   */
  T convertToModel(Map<String, String> properties);

  /**
   * Model クラスのバリデーションチェックを実行する。
   *
   * @param model チェック対象とする Model クラスを指定する。
   * @return result バリデーションチェックの結果を真偽値で返す。
   *     <ul>
   *       <li>true: バリデーションエラーがないことを表す。
   *       <li>false: バリデーションエラーが発生したことを表す。
   *     </ul>
   */
  default boolean validate(final T model) {
    boolean result = false;

    final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    final Set<ConstraintViolation<T>> resultSet = validator.validate(model);

    if (resultSet.isEmpty()) {
      result = true;
    } else {
      final Logger logger = this.getLogger();

      resultSet.stream()
          .forEach(
              error -> {
                final String path = error.getPropertyPath().toString();
                final String message = error.getMessage();
                logger.warn("{}: {}", path, message);
              });
    }

    return result;
  }

  /**
   * 操作を実行する。
   *
   * @param properties プロパティ群を収めた {@link Map} を指定する。
   * @return status 終了ステータスを返す。
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 操作を実行して正常終了したことを表す。
   *     </ul>
   */
  Status apply(Map<String, String> properties);
}
