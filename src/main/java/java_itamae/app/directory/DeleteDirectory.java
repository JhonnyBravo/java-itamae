package java_itamae.app.directory;

import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java_itamae.domain.model.Attribute;
import java_itamae.domain.service.directory.DirectoryService;
import java_itamae.domain.service.directory.DirectoryServiceImpl;

/**
 * ディレクトリを削除する。
 */
public class DeleteDirectory
        implements
            BiFunction<Attribute, Boolean, Integer> {
    /**
     * ディレクトリを削除する。
     *
     * @param attr
     *            操作対象とするディレクトリの情報を収めた Attribute を指定する。
     * @param recursive
     *            <ul>
     *            <li>true: 操作対象とするディレクトリの配下に存在するファイル・ディレクトリも含めて削除する。</li>
     *            <li>false:
     *            ディレクトリを削除する。操作対象とするディレクトリの配下にファイル・ディレクトリが存在する場合はエラーとなる。</li>
     *            </ul>
     * @return status
     *         <ul>
     *         <li>0: 操作を実行しなかったことを表す。</li>
     *         <li>1: エラーが発生したことを表す。</li>
     *         <li>2: 操作を実行したことを表す。</li>
     *         </ul>
     */
    @Override
    public Integer apply(Attribute attr, Boolean recursive) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        DirectoryService service = new DirectoryServiceImpl();
        service.setRecursive(recursive);

        try {
            boolean result = service.delete(attr);

            if (result) {
                return 2;
            } else {
                return 0;
            }
        } catch (Exception e) {
            logger.warn(e.toString());
            return 1;
        }
    }

}
