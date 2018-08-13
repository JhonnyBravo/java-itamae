package mode_resource;

import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

import org.apache.commons.lang3.math.NumberUtils;

import status_resource.StatusController;

/**
 * PosixFilePermission オブジェクトを生成する。
 */
public class CreatePermission extends StatusController {
    private String mode;

    /**
     * @param mode 生成するパーミッションを数値形式で指定する。
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
    public CreatePermission(String mode) {
        this.mode = mode;
    }

    /**
     * PosixFilePermission オブジェクトを生成して返す。
     * 
     * @return Set&lt;PosixFilePermission&gt;
     */
    public Set<PosixFilePermission> runCommand() {
        this.initStatus();

        Set<PosixFilePermission> permission = null;

        // mode の入力値が 3 桁の数列であること。
        if (NumberUtils.isDigits(this.mode) == false) {
            this.setCode(1);
            this.setMessage("mode は 3 桁の数列で指定してください。");
            this.errorTerminate();
            return permission;
        }

        if (this.mode.replace("-", "").length() != 3 | this.mode.replace("+", "").length() != 3) {
            this.setCode(1);
            this.setMessage("mode は 3 桁の数列で指定してください。");
            this.errorTerminate();
            return permission;
        }

        int userMode = Integer.parseInt(this.mode.substring(0, 1));
        int groupMode = Integer.parseInt(this.mode.substring(1, 2));
        int othersMode = Integer.parseInt(this.mode.substring(2, 3));

        // userMode, groupMode, othersMode が 0 から 7 の整数であること。
        if (userMode > 7 | groupMode > 7 | othersMode > 7) {
            this.setCode(1);
            this.setMessage("mode は 0 から 7 までの整数で構成してください。");
            this.errorTerminate();
            return permission;
        }

        String userPermission = this.convertToPermissionString(userMode);
        String groupPermission = this.convertToPermissionString(groupMode);
        String othersPermission = this.convertToPermissionString(othersMode);

        String permString = userPermission + groupPermission + othersPermission;

        permission = PosixFilePermissions.fromString(permString);
        this.setCode(2);
        return permission;
    }

    /**
     * @param bit パーミッションを表す数値を指定する。
     * @return permission パーミッションを表す数値を rwx 形式へ変換して返す。
     */
    private String convertToPermissionString(int bit) {
        String permission = null;

        if (bit == 0) {
            permission = "---";
        } else if (bit == 1) {
            permission = "--x";
        } else if (bit == 2) {
            permission = "-w-";
        } else if (bit == 3) {
            permission = "-wx";
        } else if (bit == 4) {
            permission = "r--";
        } else if (bit == 5) {
            permission = "r-x";
        } else if (bit == 6) {
            permission = "rw-";
        } else if (bit == 7) {
            permission = "rwx";
        }

        return permission;
    }
}
