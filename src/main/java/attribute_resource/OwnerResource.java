package attribute_resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * ファイルまたはディレクトリの所有者を管理する。
 */
public class OwnerResource extends AttributeResource<UserPrincipal> {
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

        logger = Logger.getLogger(this.getClass().getName());
        logger.addHandler(new ConsoleHandler());
        logger.setUseParentHandlers(false);
    }

    /**
     * @return UserPrincipal 新規生成された UserPrincipal を返す。
     * @throws IOException {@link java.io.IOException}
     *
     * @see attribute_resource.AttributeResource#createAttribute()
     */
    @Override
    protected UserPrincipal createAttribute() throws IOException {
        final UserPrincipalLookupService upls = FileSystems.getDefault().getUserPrincipalLookupService();
        final UserPrincipal up = upls.lookupPrincipalByName(owner);

        return up;
    }

    /**
     * @return UserPrincipal ファイルまたはディレクトリに現在の所有者として設定されている UserPrincipal を返す。
     * @throws IOException           {@link java.io.IOException}
     * @throws FileNotFoundException {@link java.io.FileNotFoundException}
     * @see attribute_resource.AttributeResource#getAttribute()
     */
    @Override
    protected UserPrincipal getAttribute() throws FileNotFoundException, IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(path + " が見つかりません。");
        }

        final UserPrincipal up = Files.getOwner(file.toPath());
        return up;
    }

    /**
     * ファイルまたはディレクトリの所有者を変更する。
     *
     * @throws IOException           {@link java.io.IOException}
     * @throws FileNotFoundException {@link java.io.FileNotFoundException}
     *
     * @see attribute_resource.AttributeResource#setAttribute()
     * @return status
     *         <ul>
     *         <li>true: ファイルまたはディレクトリの所有者を変更したことを表す。</li>
     *         <li>false: ファイルまたはディレクトリの所有者を変更していないことを表す。</li>
     *         </ul>
     */
    @Override
    public boolean setAttribute() throws FileNotFoundException, IOException {
        boolean status = false;

        final UserPrincipal curOwner = getAttribute();
        final UserPrincipal newOwner = createAttribute();

        if (curOwner.equals(newOwner)) {
            return status;
        }

        logger.entering(this.getClass().getName(), "setAttribute");
        logger.info("所有者を変更します。");

        try {
            Files.setOwner(file.toPath(), newOwner);
            status = true;
            return status;
        } catch (final IOException e) {
            logger.throwing(this.getClass().getName(), "setAttribute", e);
            throw e;
        }
    }
}
