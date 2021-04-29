package java_itamae_contents.app.contents;

import java.util.List;
import java.util.function.Function;
import java_itamae_contents.domain.model.ContentsAttribute;
import java_itamae_contents.domain.service.contents.ContentsService;
import java_itamae_contents.domain.service.contents.ContentsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * テキストファイルを読込み、内容を標準出力へ出力する。
 */
public class GetContents implements Function<ContentsAttribute, Integer> {
  /**
   * テキストファイルを読込み、内容を標準出力へ出力する。
   *
   * @param attr 操作対象とするファイルの情報を収めた {@link ContentsAttribute} を指定する。
   * @return result
   *         <ul>
   *         <li>0: 操作を実行しなかったことを表す。</li>
   *         <li>1: エラーが発生したことを表す。</li>
   *         <li>2: 操作を実行したことを表す。</li>
   *         </ul>
   */
  @Override
  public Integer apply(ContentsAttribute attr) {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    final ContentsService service = new ContentsServiceImpl(attr);

    try {
      final List<String> contents = service.getContents();

      if (contents.size() == 0) {
        return 0;
      } else {
        contents.forEach(line -> {
          System.out.println(line);
        });

        return 2;
      }
    } catch (final Exception e) {
      logger.warn(e.toString());
      return 1;
    }
  }
}
