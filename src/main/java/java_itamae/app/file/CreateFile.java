package java_itamae.app.file;

import java.util.function.Function;

import java_itamae.domain.model.file.FileResourceModel;
import java_itamae.domain.service.file.FileService;
import java_itamae.domain.service.file.FileServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ファイルに対して以下の操作を実行する。
 * <ul>
 * <li>新規作成</li>
 * <li>所有者の変更</li>
 * <li>グループ所有者の変更</li>
 * <li>パーミッションの変更</li>
 * </ul>
 */
public class CreateFile implements Function<FileResourceModel, Integer> {
  /**
   * ファイルに対して以下の操作を実行する。
   * <ul>
   * <li>新規作成</li>
   * <li>所有者の変更</li>
   * <li>グループ所有者の変更</li>
   * <li>パーミッションの変更</li>
   * </ul>
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
  public Integer apply(FileResourceModel attr) {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    final FileService service = new FileServiceImpl();

    try {
      final boolean result = service.create(attr);

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
