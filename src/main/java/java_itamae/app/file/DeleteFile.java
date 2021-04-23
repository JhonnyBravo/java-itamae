package java_itamae.app.file;

import java.util.function.Function;
import java_itamae.domain.model.Attribute;
import java_itamae.domain.service.file.FileService;
import java_itamae.domain.service.file.FileServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ファイルを削除する。
 */
public class DeleteFile implements Function<Attribute, Integer> {
  /**
   * ファイルを削除する。
   *
   * @param attr 操作対象とするファイルの情報を収めた Attribute を指定する。
   * @return status
   *         <ul>
   *         <li>0: 操作を実行しなかったことを表す。</li>
   *         <li>1: エラーが発生したことを表す。</li>
   *         <li>2: 操作を実行したことを表す。</li>
   *         </ul>
   */
  @Override
  public Integer apply(Attribute attr) {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    final FileService service = new FileServiceImpl();

    try {
      final boolean result = service.delete(attr);

      if (result) {
        return 2;
      } else {
        return 0;
      }
    } catch (final Exception e) {
      if (logger.isWarnEnabled()) {
        logger.warn(e.toString());
      }

      return 1;
    }
  }

}
