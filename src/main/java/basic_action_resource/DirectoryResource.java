package basic_action_resource;

import java.io.File;
import java.io.FilenameFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import attribute_resource.AttributeResource;
import attribute_resource.GroupResource;
import attribute_resource.ModeResource;
import attribute_resource.OwnerResource;

/**
 * ディレクトリの作成・削除と、所有者・グループ所有者・パーミッションの設定変更を実行する。
 */
public class DirectoryResource implements ActionResource {
    private final String path;
    private final File file;
    private FilenameFilter filter;

    private final Logger logger;
    private AttributeResource<?> attribute;

    /**
     * @param path 操作対象とするディレクトリのパスを指定する。
     */
    public DirectoryResource(String path) {
        this.path = path;
        file = new File(path);
        filter = null;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * ディレクトリを作成する。
     *
     * @return status
     *         <ul>
     *         <li>true: ディレクトリが作成されたことを表す。</li>
     *         <li>false: ディレクトリが作成されなかったことを表す。</li>
     *         </ul>
     */
    @Override
    public boolean create() {
        boolean status = false;

        if (!file.isDirectory()) {
            logger.info(path + " を作成します。");
            status = file.mkdirs();
        }

        return status;
    }

    /**
     * ディレクトリを削除する。
     *
     * @return status
     *         <ul>
     *         <li>true: ディレクトリが削除されたことを表す。</li>
     *         <li>false: ディレクトリが削除されなかったことを表す。</li>
     *         </ul>
     */
    @Override
    public boolean delete() {
        boolean status = false;

        if (file.isDirectory()) {
            logger.info(path + " を削除します。");
            status = deleteDirectory(file);
        }

        return status;
    }

    /**
     * ディレクトリを再帰的に削除する。
     *
     * @param directory 削除対象とするディレクトリの File オブジェクトを指定する。
     * @return status
     *         <ul>
     *         <li>true: ディレクトリが削除されたことを表す。</li>
     *         <li>false: ディレクトリが削除されなかったことを表す。</li>
     *         </ul>
     */
    private boolean deleteDirectory(File directory) {
        boolean status = false;

        for (final File f : directory.listFiles()) {
            deleteDirectory(f);
        }

        status = directory.delete();
        return status;
    }

    /**
     * ディレクトリの所有者を変更する。
     *
     * @param owner 新しい所有者として設定するユーザ名を指定する。
     * @return status
     *         <ul>
     *         <li>true: 所有者が変更されたことを表す。</li>
     *         <li>false: 所有者が変更されなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    @Override
    public boolean setOwner(String owner) throws Exception {
        boolean status = false;

        if (!file.isDirectory()) {
            logger.warn(path + " はディレクトリではありません。");
            return status;
        } else {
            attribute = new OwnerResource(path, owner);
            status = attribute.setAttribute();
        }

        return status;
    }

    /**
     * ディレクトリのグループ所有者を変更する。
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

        if (!file.isDirectory()) {
            logger.warn(path + " はディレクトリではありません。");
        } else {
            attribute = new GroupResource(path, group);
            status = attribute.setAttribute();
        }

        return status;
    }

    /**
     * ディレクトリのパーミッション設定を変更する。
     *
     * @param mode 新しく設定するパーミッション値を 3 桁の整数で指定する。
     * @return status
     *         <ul>
     *         <li>true: パーミッションが変更されたことを表す。</li>
     *         <li>false: パーミッションが変更されなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    @Override
    public boolean setMode(String mode) throws Exception {
        boolean status = false;

        if (!file.isDirectory()) {
            logger.warn(path + " はディレクトリではありません。");
        } else {
            attribute = new ModeResource(path, mode);
            status = attribute.setAttribute();
        }

        return status;
    }

    /**
     * FileFilter を生成する。
     *
     * @param extension 取得対象とするファイルの拡張子を指定する。
     */
    public void setFileFilter(String extension) {
        filter = (dir, name) -> {
            if (name.toLowerCase().endsWith(extension)) {
                return true;
            } else {
                return false;
            }
        };
    }

    /**
     * @return ディレクトリの直下に存在するファイルの一覧を取得して返す。
     */
    public File[] getFiles() {
        File[] result = null;

        if (!file.isDirectory()) {
            logger.warn(path + " が見つかりません。");
            result = new File[0];
            return result;
        }

        if (filter == null) {
            result = file.listFiles();
        } else {
            result = file.listFiles(filter);
        }

        return result;
    }
}
