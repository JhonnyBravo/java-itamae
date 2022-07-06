package java_itamae.app.directory;

import java.util.function.Function;
import java_itamae.domain.model.directory.DirectoryResourceModel;
import java_itamae.domain.service.directory.DirectoryService;
import java_itamae.domain.service.directory.DirectoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** ディレクトリを削除する。 */
public class DeleteDirectory implements Function<DirectoryResourceModel, Integer> {
  /**
   * ディレクトリを削除する。
   *
   * @param model 操作対象とするディレクトリの情報を収めた Attribute を指定する。
   * @param recursive
   *     <ul>
   *       <li>true: 操作対象とするディレクトリの配下に存在するファイル・ディレクトリも含めて削除する。
   *       <li>false: ディレクトリを削除する。操作対象とするディレクトリの配下にファイル・ディレクトリが存在する場合はエラーとなる。
   *     </ul>
   *
   * @return status
   *     <ul>
   *       <li>0: 操作を実行しなかったことを表す。
   *       <li>1: エラーが発生したことを表す。
   *       <li>2: 操作を実行したことを表す。
   *     </ul>
   */
  @Override
  public Integer apply(DirectoryResourceModel model) {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    final DirectoryService service = new DirectoryServiceImpl();
    service.setRecursive(model.isRecursive());

    try {
      final boolean result = service.delete(model);

      if (result) {
        return 2;
      }
      return 0;
    } catch (final Exception e) {
      if (logger.isWarnEnabled()) {
        logger.warn(e.toString());
      }

      return 1;
    }
  }
}
