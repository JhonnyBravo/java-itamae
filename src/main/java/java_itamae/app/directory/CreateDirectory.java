package java_itamae.app.directory;

import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java_itamae.domain.model.Attribute;
import java_itamae.domain.service.directory.DirectoryService;
import java_itamae.domain.service.directory.DirectoryServiceImpl;

/**
 * ディレクトリに対して以下の操作を実行する。
 * <ul>
 * <li>新規作成</li>
 * <li>所有者の変更</li>
 * <li>グループ所有者の変更</li>
 * <li>パーミッションの変更</li>
 * </ul>
 */
public class CreateDirectory
        implements
            BiFunction<Attribute, Boolean, Integer> {
    /**
     * ディレクトリに対して以下の操作を実行する。
     * <ul>
     * <li>新規作成</li>
     * <li>所有者の変更</li>
     * <li>グループ所有者の変更</li>
     * <li>パーミッションの変更</li>
     * </ul>
     *
     * @param attr
     *            操作対象とするディレクトリの情報を収めた Attribute を指定する。
     * @param recursive
     *            <ul>
     *            <li>true: 複数階層のディレクトリを親ディレクトリも含めて作成する。</li>
     *            <li>false: 単一階層のディレクトリを作成する。</li>
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
            boolean result = service.create(attr);

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
