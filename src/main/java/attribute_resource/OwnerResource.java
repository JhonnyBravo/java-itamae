package attribute_resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ファイルまたはディレクトリの所有者を管理する。
 */
public class OwnerResource implements AttributeResource<UserPrincipal> {
    private final Logger logger;

    private final String path;
    private final String owner;
    private final File file;

    /**
     * @param path  操作対象とするファイルまたはディレクトリのパスを指定する。
     * @param owner 新しい所有者として設定するユーザ名を指定する。
     */
    public OwnerResource(String path, String owner) {
        this.path = path;
        file = new File(path);
        this.owner = owner;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * @return UserPrincipal 新規生成された UserPrincipal を返す。
     * @throws IOException {@link java.io.IOException}
     *
     * @see attribute_resource.AttributeResource#createAttribute()
     */
    @Override
    public UserPrincipal createAttribute() throws IOException {
        final UserPrincipalLookupService upls = FileSystems.getDefault().getUserPrincipalLookupService();
        final UserPrincipal up = upls.lookupPrincipalByName(owner);

        return up;
    }

    /**
     * @return UserPrincipal ファイルまたはディレクトリに現在の所有者として設定されている UserPrincipal を返す。
     * @throws IOException {@link java.io.IOException}
     * @see attribute_resource.AttributeResource#getAttribute()
     */
    @Override
    public UserPrincipal getAttribute() throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(path + " が見つかりません。");
        }

        final UserPrincipal up = Files.getOwner(file.toPath());
        return up;
    }

    /**
     * ファイルまたはディレクトリの所有者を変更する。
     *
     * @throws IOException {@link java.io.IOException}
     *
     * @see attribute_resource.AttributeResource#setAttribute()
     * @return status
     *         <ul>
     *         <li>true: ファイルまたはディレクトリの所有者を変更したことを表す。</li>
     *         <li>false: ファイルまたはディレクトリの所有者を変更していないことを表す。</li>
     *         </ul>
     */
    @Override
    public boolean setAttribute() throws IOException {
        boolean status = false;

        final UserPrincipal curOwner = getAttribute();
        final UserPrincipal newOwner = createAttribute();

        if (curOwner.equals(newOwner)) {
            return status;
        }

        logger.info("所有者を変更します。");
        Files.setOwner(file.toPath(), newOwner);

        status = true;
        return status;
    }
}
