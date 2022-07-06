package java_itamae.app.template;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.service.contents.ContentsService;
import java_itamae.domain.service.contents.ContentsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** テキストファイルの内容を読み込む。 */
public class GetContents implements Function<ContentsModel, List<String>> {
  @Override
  /**
   * テキストファイルの内容を読込み {@link List} 変換して返す。
   *
   * @param model 読込み対象とするテキストファイルの情報を収めた {@link ContentsModel} を指定する。
   * @return contents ファイルの内容を変換した {@link List}
   */
  public List<String> apply(ContentsModel model) {
    final ContentsService service = new ContentsServiceImpl(model);
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    List<String> contents = new ArrayList<>();

    try {
      contents = service.getContents();
    } catch (final Exception e) {
      logger.warn(e.toString());
    }

    return contents;
  }
}
