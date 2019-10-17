package basic_action_resource.domain.repository.action;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import basic_action_resource.domain.repository.attribute.AttributeResource;
import basic_action_resource.domain.repository.attribute.GroupResource;
import basic_action_resource.domain.repository.attribute.ModeResource;
import basic_action_resource.domain.repository.attribute.OwnerResource;

/**
 * ファイルの作成・削除と、所有者・グループ所有者・パーミッションの設定変更を実行する。
 */
public class FileResource implements ActionResource {
    private final String path;
    private final File file;

    private final Logger logger;
    private AttributeResource<?> attribute;

    /**
     * @param path 操作対象とするファイルのパスを指定する。
     */
    public FileResource(String path) {
        this.path = path;
        file = new File(path);
        logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * ファイルを新規作成する。
     *
     * @return status
     *         <ul>
     *         <li>true: ファイルが作成されたことを表す。</li>
     *         <li>false: ファイルが作成されなかったことを表す。</li>
     *         </ul>
     * @throws IOException {@link java.io.IOException}
     */
    @Override
    public boolean create() throws IOException {
        boolean status = false;

        if (!file.isFile()) {
            logger.info(path + " を作成します。");
            status = file.createNewFile();
        }

        return status;
    }

    /**
     * ファイルを削除する。
     *
     * @return status
     *         <ul>
     *         <li>true: ファイルが削除されたことを表す。</li>
     *         <li>true: ファイルが削除されなかったことを表す。</li>
     *         </ul>
     */
    @Override
    public boolean delete() {
        boolean status = false;

        if (file.isFile()) {
            logger.info(path + " を削除します。");
            status = file.delete();
        }

        return status;
    }

    /**
     * ファイルの所有者を変更する。
     *
     * @param owner 新しい所有者として設定するユーザ名を指定する。
     * @return status
     *         <ul>
     *         <li>true: 所有者が変更されたことを表す。</li>
     *         <li>true: 所有者が変更されなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    @Override
    public boolean setOwner(String owner) throws Exception {
        boolean status = false;

        if (!file.isFile()) {
            logger.warn(path + " はファイルではありません。");
        } else {
            attribute = new OwnerResource(path, owner);
            status = attribute.setAttribute();
        }

        return status;
    }

    /**
     * ファイルのグループ所有者を変更する。
     *
     * @param group 新しいグループ所有者として設定するグループ名を指定する。
     * @return status
     *         <ul>
     *         <li>true: グループ所有者が変更されたことを表す。</li>
     *         <li>false: グループ所有者が変更されなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    @Override
    public boolean setGroup(String group) throws Exception {
        boolean status = false;

        if (!file.isFile()) {
            logger.warn(path + " はファイルではありません。");
        } else {
            attribute = new GroupResource(path, group);
            status = attribute.setAttribute();
        }

        return status;
    }

    /**
     * ファイルのパーミッション設定を変更する。
     *
     * @param mode 新しく設定するパーミッション値を 3 桁の整数で指定する。
     * @return status
     *         <ul>
     *         <li>パーミッション設定が変更されたことを表す。</li>
     *         <li>パーミッション設定が変更されなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    @Override
    public boolean setMode(String mode) throws Exception {
        boolean status = false;

        if (!file.isFile()) {
            logger.warn(path + " はファイルではありません。");
        } else {
            attribute = new ModeResource(path, mode);
            status = attribute.setAttribute();
        }

        return status;
    }
}
