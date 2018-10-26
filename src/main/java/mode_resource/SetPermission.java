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
    private ModeProperties mp = new ModeProperties();
    private GetPermission gp = new GetPermission();
    private CreatePermission cp = new CreatePermission();

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
    public void init(String path, String mode) {
        mp.setPath(path);
        mp.setMode(mode);
    }

    /**
     * ファイルまたはディレクトリのアクセス権限を変更する。
     */
    public void runCommand() {
        this.initStatus();

        // 現在のアクセス権限を取得する。
        gp.init(mp.getPath());
        Set<PosixFilePermission> curPermission = gp.runCommand();

        if (gp.getCode() == 1) {
            this.setCode(gp.getCode());
            return;
        }

        // 新しく設定するアクセス権限を生成する。
        cp.init(mp.getMode());
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
        Path p = new File(mp.getPath()).toPath();

        try {
            Files.setPosixFilePermissions(p, newPermission);
            this.setCode(2);
        } catch (IOException e) {
            this.setMessage("エラーが発生しました。 " + e.toString());
            this.errorTerminate();
        }
    }
}
