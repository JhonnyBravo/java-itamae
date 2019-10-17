package attribute_resource.domain.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserPrincipalLookupService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ファイルまたはディレクトリのグループ所有者を管理する。
 */
public class GroupResource implements AttributeResource<GroupPrincipal> {
    private final Logger logger;
    private final String path;
    private final String group;
    private final File file;

    /**
     * @param path  操作対象とするファイルまたはディレクトリのパスを指定する。
     * @param group 新しいグループ所有者として設定するグループ名を指定する。
     */
    public GroupResource(String path, String group) {
        this.path = path;
        this.group = group;
        file = new File(path);
        logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * @return GroupPrincipal 新規生成された GroupPrincipal を返す。
     * @throws IOException {@link java.io.IOException}
     *
     * @see attribute_resource.domain.repository.AttributeResource#createAttribute()
     */
    @Override
    public GroupPrincipal createAttribute() throws IOException {
        final UserPrincipalLookupService upls = FileSystems.getDefault().getUserPrincipalLookupService();
        final GroupPrincipal gp = upls.lookupPrincipalByGroupName(group);

        return gp;
    }

    /**
     * @return GroupPrincipal ファイルまたはディレクトリに現在のグループ所有者として設定されている GroupPrincipal
     *         を取得する。
     * @throws IOException {@link java.io.IOException}
     * @see attribute_resource.domain.repository.AttributeResource#getAttribute()
     */
    @Override
    public GroupPrincipal getAttribute() throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(path + " が見つかりません。");
        }

        final PosixFileAttributeView pfav = Files.getFileAttributeView(file.toPath(), PosixFileAttributeView.class);
        final GroupPrincipal gp = pfav.readAttributes().group();

        return gp;
    }

    /**
     * ファイルまたはディレクトリのグループ所有者を変更する。
     *
     * @throws IOException {@link java.io.IOException}
     * @see attribute_resource.domain.repository.AttributeResource#setAttribute()
     * @return status
     *         <ul>
     *         <li>true: ファイルまたはディレクトリのグループ所有者を変更したことを表す。</li>
     *         <li>false: ファイルまたはディレクトリのグループ所有者を変更していないことを表す。</li>
     *         </ul>
     */
    @Override
    public boolean setAttribute() throws IOException {
        boolean status = false;
        final String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            logger.warn(osName + " ではグループ所有者の取得 / 設定はサポートしていません。");
            return status;
        }

        final GroupPrincipal curGroup = getAttribute();
        final GroupPrincipal newGroup = createAttribute();

        if (curGroup.equals(newGroup)) {
            return status;
        }

        logger.info("グループ所有者を変更します。");
        final PosixFileAttributeView pfav = Files.getFileAttributeView(file.toPath(), PosixFileAttributeView.class);
        pfav.setGroup(newGroup);

        status = true;
        return status;
    }
}
