package attribute_resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

/**
 * ファイルまたはディレクトリのパーミッション設定を管理する。
 */
public class ModeResource extends AttributeResource {
    private String path;
    private String mode;
    private File file;

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
        this.file = new File(path);
        this.mode = mode;
    }

    /**
     * @return Object ファイルまたはディレクトリに現在のパーミッションとして設定されている PosixFilePermission を取得する。
     * 
     * @see attribute_resource.AttributeResource#getAttribute()
     */
    @Override
    protected Object getAttribute() {
        this.initStatus();

        Set<PosixFilePermission> permission = null;

        if (!this.file.exists()) {
            this.errorTerminate(this.path + " が見つかりません。");
            return permission;
        }

        try {
            permission = Files.getPosixFilePermissions(this.file.toPath());
            this.setCode(2);
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。 " + e);
        }

        return permission;
    }

    /**
     * @return Object 新規生成された PosixFilePermission を返す。
     * 
     * @see attribute_resource.AttributeResource#createAttribute()
     */
    @Override
    protected Object createAttribute() {
        this.initStatus();

        Set<PosixFilePermission> permission = null;

        if (!this.mode.matches("[0-7]{3}")) {
            this.errorTerminate("mode の値が不正です。 0 から 7 までの整数のみで構成された 3 桁の数列を指定してください。");
            return permission;
        }

        String converted = this.mode.replaceAll("0", "---").replaceAll("1", "--x").replaceAll("2", "-w-")
                .replaceAll("3", "-wx").replaceAll("4", "r--").replaceAll("5", "r-x").replaceAll("6", "-wx")
                .replaceAll("7", "rwx");
        permission = PosixFilePermissions.fromString(converted);

        return permission;
    }

    /**
     * ファイルまたはディレクトリのパーミッション設定を変更する。
     * 
     * @see attribute_resource.AttributeResource#setAttribute()
     */
    @Override
    public void setAttribute() {
        this.initStatus();

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            this.errorTerminate(osName + " ではパーミッションの取得 / 設定はサポートしていません。");
            return;
        }

        @SuppressWarnings("unchecked")
        Set<PosixFilePermission> curPermission = (Set<PosixFilePermission>) this.getAttribute();

        if (this.getCode() == 1) {
            return;
        }

        @SuppressWarnings("unchecked")
        Set<PosixFilePermission> newPermission = (Set<PosixFilePermission>) this.createAttribute();

        if (this.getCode() == 1) {
            return;
        }

        if (curPermission.equals(newPermission)) {
            this.initStatus();
            return;
        }

        System.out.println("パーミッションを変更します。");

        try {
            Files.setPosixFilePermissions(this.file.toPath(), newPermission);
            this.setCode(2);
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。 " + e);
        }
    }

}
