package java_itamae_contents.app.contents;

import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java_itamae_contents.domain.model.ContentsAttribute;
import java_itamae_contents.domain.service.contents.ContentsService;
import java_itamae_contents.domain.service.contents.ContentsServiceImpl;

/**
 * テキストファイルの末尾へ文字列を追記する。
 */
public class AppendContent implements BiFunction<ContentsAttribute, String, Integer> {
    /**
     * テキストファイルの末尾へ文字列を追記する。
     *
     * @param attr 操作対象とするファイルの情報を収めた {@link ContentsAttribute} を指定する。
     * @param line ファイルへ追記する文字列を指定する。
     * @return result
     *         <ul>
     *         <li>0: 操作を実行しなかったことを表す。</li>
     *         <li>1: エラーが発生したことを表す。</li>
     *         <li>2: 操作を実行したことを表す。</li>
     *         </ul>
     */
    @Override
    public Integer apply(ContentsAttribute attr, String line) {
        final Logger logger = LoggerFactory.getLogger(this.getClass());
        final ContentsService service = new ContentsServiceImpl(attr);

        try {
            final boolean result = service.appendContent(line);

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
