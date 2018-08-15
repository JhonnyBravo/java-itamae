package mode_resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

import status_resource.StatusController;

/**
 * ファイルまたはディレクトリのアクセス権限を変更する。
 */
public class SetPermission extends StatusController {
    private String path;
    private String mode;

    /**
     * @param path 操作対象とするファイルまたはディレクトリのパスを指定する。
     * @param mode 変更後のアクセス権限を数値で指定する。
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
    public SetPermission(String path, String mode) {
        this.path = path;
        this.mode = mode;
    }

    /**
     * ファイルまたはディレクトリのアクセス権限を変更する。
     */
    public void runCommand() {
        this.initStatus();

        // 現在のアクセス権限を取得する。
        GetPermission gp = new GetPermission(this.path);
        Set<PosixFilePermission> curPermission = gp.runCommand();

        if (gp.getCode() == 1) {
            this.setCode(gp.getCode());
            return;
        }

        // 新規設定するアクセス権限を取得する。
        CreatePermission cp = new CreatePermission(this.mode);
        Set<PosixFilePermission> newPermission = cp.runCommand();

        if (cp.getCode() == 1) {
            this.setCode(cp.getCode());
            return;
        }

        // 現在のアクセス権限と新しいアクセス権限が同一である場合は何もせずに終了する。
        if (curPermission.equals(newPermission)) {
            this.initStatus();
            return;
        }

        // 新しいアクセス権限を設定する。
        System.out.println("パーミッションを変更します。");
        Path p = new File(this.path).toPath();

        try {
            Files.setPosixFilePermissions(p, newPermission);
            this.setCode(2);
        } catch (IOException e) {
            this.setCode(1);
            this.setMessage("エラーが発生しました。 " + e.toString());
            this.errorTerminate();
        }
    }
}
