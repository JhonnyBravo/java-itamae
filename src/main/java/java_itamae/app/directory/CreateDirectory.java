package java_itamae.app.directory;

import java.util.function.Function;
import java_itamae.domain.model.directory.DirectoryResourceModel;
import java_itamae.domain.service.directory.DirectoryService;
import java_itamae.domain.service.directory.DirectoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ディレクトリに対して以下の操作を実行する。
 *
 * <ul>
 *   <li>新規作成
 *   <li>所有者の変更
 *   <li>グループ所有者の変更
 *   <li>パーミッションの変更
 * </ul>
 */
public class CreateDirectory implements Function<DirectoryResourceModel, Integer> {
  /**
   * ディレクトリに対して以下の操作を実行する。
   *
   * <ul>
   *   <li>新規作成
   *   <li>所有者の変更
   *   <li>グループ所有者の変更
   *   <li>パーミッションの変更
   * </ul>
   *
   * @param model 操作対象とするディレクトリの情報を収めた Attribute を指定する。
   * @param recursive
   *     <ul>
   *       <li>true: 複数階層のディレクトリを親ディレクトリも含めて作成する。
   *       <li>false: 単一階層のディレクトリを作成する。
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
      final boolean result = service.create(model);

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
