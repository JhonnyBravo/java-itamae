package java_itamae.app.file;

import java.util.function.Function;
import java_itamae.domain.model.Attribute;
import java_itamae.domain.service.file.FileService;
import java_itamae.domain.service.file.FileServiceImpl;
import javax.enterprise.inject.New;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ファイルを削除する。
 */
public class DeleteFile implements Function<Attribute, Integer> {
  @Inject
  @New(FileServiceImpl.class)
  private FileService service;

  /**
   * ファイルを削除する。
   *
   * @param attr 操作対象とするファイルの情報を収めた {@link Attribute} を指定する。
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
