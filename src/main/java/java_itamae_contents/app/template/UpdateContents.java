package java_itamae_contents.app.template;

import java.util.List;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java_itamae_contents.domain.model.contents.ContentsModel;
import java_itamae_contents.domain.service.contents.ContentsService;
import java_itamae_contents.domain.service.contents.ContentsServiceImpl;

/** テキストファイルの内容を上書きする。 */
public class UpdateContents implements BiFunction<ContentsModel, List<String>, Integer> {
  @Override
  /**
   * テキストファイルの内容を上書きする。
   *
   * @param model 書込み対象とするテキストファイルの情報を収めた {@link ContentsModel} を指定する。
   * @return status 終了ステータスを返す。
   *     <ul>
   *       <li>0: 何も実行せずに終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 書込みに成功したことを表す。
   *     </ul>
   */
  public Integer apply(ContentsModel model, List<String> contents) {
    final ContentsService service = new ContentsServiceImpl(model);
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    int status = 0;

    try {
      final boolean result = service.updateContents(contents);

      if (result) {
        status = 2;
      }
    } catch (final Exception e) {
      logger.warn(e.toString());
      status = 1;
    }

    return status;
  }
}
