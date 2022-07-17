package java_itamae.domain.component.common;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Component クラスの土台となるインターフェイス。 */
public interface BaseComponent {
  /**
   * {@link Logger} を生成して返す。
   *
   * @return logger {@link Logger}
   */
  public default Logger getLogger() {
    return LoggerFactory.getLogger(this.getClass());
  }

  /**
   * 指定した path を {@link Path} に変換して返す。
   *
   * @param path 変換対象とするファイル/ディレクトリのパスを指定する。
   * @return path 変換された {@link Path}
   */
  public default Path convertToPath(String path) {
    return FileSystems.getDefault().getPath(path);
  }
}
