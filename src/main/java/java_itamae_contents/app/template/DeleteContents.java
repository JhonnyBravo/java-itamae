package java_itamae_contents.app.template;

import java.util.function.Function;
import java_itamae_contents.domain.model.contents.ContentsModel;
import java_itamae_contents.domain.service.contents.ContentsService;
import java_itamae_contents.domain.service.contents.ContentsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** テキストファイルの内容を削除して空にする。 */
public class DeleteContents implements Function<ContentsModel, Integer> {
  /**
   * テキストファイルの内容を削除して空にする。
   *
   * @param attr 操作対象とするファイルの情報を収めた {@link ContentsModel} を指定する。
   * @return result
   *     <ul>
   *       <li>0: 操作を実行しなかったことを表す。
   *       <li>1: エラーが発生したことを表す。
   *       <li>2: 操作を実行したことを表す。
   *     </ul>
   */
  @Override
  public Integer apply(ContentsModel attr) {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    final ContentsService service = new ContentsServiceImpl(attr);

    try {
      final boolean result = service.deleteContents();

      if (result) {
        return 2;
      } else {
        return 0;
      }
    } catch (final Exception e) {
      logger.warn(e.toString());
      return 1;
    }
  }
}
