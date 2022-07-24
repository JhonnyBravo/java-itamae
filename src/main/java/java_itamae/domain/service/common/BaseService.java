package java_itamae.domain.service.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Service クラスの土台となるインターフェイス。 */
public interface BaseService {
  /**
   * {@link Logger} を生成して返す。
   *
   * @return logger {@link Logger}
   */
  public default Logger getLogger() {
    return LoggerFactory.getLogger(this.getClass());
  }
}
