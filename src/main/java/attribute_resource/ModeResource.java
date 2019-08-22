package attribute_resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * ファイルまたはディレクトリのパーミッション設定を管理する。
 */
public class ModeResource extends AttributeResource<Set<PosixFilePermission>> {
    private final Logger logger;
    private final String path;
    private final String mode;
    private final File file;

    /**
     * @param path 操作対象とするファイルまたはディレクトリのパスを指定する。
     * @param mode 新しいパーミッションとして設定する値を 0 から 7 までの整数のみで構成された 3 桁の数列で指定する。
     *             <ul>
     *             <li>0: ---</li>
     *             <li>1: --x</li>
     *             <li>2: -w-</li>
     *             <li>3: -wx</li>
     *             <li>4: r--</li>
     *             <li>5: r-x</li>
     *             <li>6: rw-</li>
     *             <li>7: rwx</li>
     *             </ul>
     */
    public ModeResource(String path, String mode) {
        this.path = path;
        file = new File(path);
        this.mode = mode;

        logger = Logger.getLogger(this.getClass().getName());
        logger.addHandler(new ConsoleHandler());
    }

    /**
     * @return Set&lt;PosixFilePermission&gt; 新規生成された PosixFilePermission を返す。
     *
     * @see attribute_resource.AttributeResource#createAttribute()
     */
    @Override
    protected Set<PosixFilePermission> createAttribute() {
        Set<PosixFilePermission> permission = null;

        if (!mode.matches("[0-7]{3}")) {
            logger.entering(this.getClass().getName(), "createAttribute");
            logger.warning("mode の値が不正です。 0 から 7 までの整数のみで構成された 3 桁の数列を指定してください。");
            return permission;
        }

        final String converted = mode.replaceAll("0", "---").replaceAll("1", "--x").replaceAll("2", "-w-")
                .replaceAll("3", "-wx").replaceAll("4", "r--").replaceAll("5", "r-x").replaceAll("6", "rw-")
                .replaceAll("7", "rwx");
        permission = PosixFilePermissions.fromString(converted);

        return permission;
    }

    /**
     * @return Set&lt;PosixFilePermission&gt; ファイルまたはディレクトリに現在のパーミッションとして設定されている
     *         PosixFilePermission を取得する。
     * @throws IOException           {@link java.io.IOException}
     * @throws FileNotFoundException {@link java.io.FileNotFoundException}
     *
     * @see attribute_resource.AttributeResource#getAttribute()
     */
    @Override
    protected Set<PosixFilePermission> getAttribute() throws FileNotFoundException, IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(path + " が見つかりません。");
        }

        final Set<PosixFilePermission> permission = Files.getPosixFilePermissions(file.toPath());
        return permission;
    }

    /**
     * ファイルまたはディレクトリのパーミッション設定を変更する。
     *
     * @throws IOException           {@link java.io.IOException}
     * @throws FileNotFoundException {@link java.io.FileNotFoundException}
     *
     * @see attribute_resource.AttributeResource#setAttribute()
     * @return status
     *         <ul>
     *         <li>true: ファイルまたはディレクトリのパーミッション設定を変更したことを表す。</li>
     *         <li>false: ファイルまたはディレクトリのパーミッション設定を変更していないことを表す。</li>
     *         </ul>
     */
    @Override
    public boolean setAttribute() throws FileNotFoundException, IOException {
        boolean status = false;
        logger.entering(this.getClass().getName(), "setAttribute");

        final String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            logger.warning(osName + " ではパーミッションの取得 / 設定はサポートしていません。");
            return status;
        }

        final Set<PosixFilePermission> curPermission = getAttribute();
        final Set<PosixFilePermission> newPermission = createAttribute();

        if (newPermission == null) {
            return status;
        }

        if (curPermission.equals(newPermission)) {
            return status;
        }

        logger.info("パーミッションを変更します。");

        try {
            Files.setPosixFilePermissions(file.toPath(), newPermission);
            status = true;
            return status;
        } catch (final IOException e) {
            logger.throwing(this.getClass().getName(), "setAttribute", e);
            throw e;
        }
    }
}
