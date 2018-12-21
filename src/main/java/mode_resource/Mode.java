package mode_resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

import org.apache.commons.lang3.math.NumberUtils;

import status_resource.Status;

/**
 * @see mode_resource.ModeResource
 */
public class Mode extends Status implements ModeResource {
    /**
     * @param mode パーミッション値を 0 から 7 までの整数で指定する。
     * @return permission パーミッション値を rwx 形式の文字列に変換して返す。
     */
    private String toStringFromMode(int mode) {
        String result = null;

        if (mode == 0) {
            result = "---";
        } else if (mode == 1) {
            result = "--x";
        } else if (mode == 2) {
            result = "-w-";
        } else if (mode == 3) {
            result = "-wx";
        } else if (mode == 4) {
            result = "r--";
        } else if (mode == 5) {
            result = "r-x";
        } else if (mode == 6) {
            result = "rw-";
        } else if (mode == 7) {
            result = "rwx";
        }

        return result;
    }

    /*
     * @see mode_resource.ModeResource#createMode(java.lang.String)
     */
    @Override
    public Set<PosixFilePermission> createMode(String mode) {
        this.initStatus();

        Set<PosixFilePermission> permission = null;

        if (!NumberUtils.isDigits(mode)) {
            this.errorTerminate("mode は 3 桁の数列で指定してください。");
            return permission;
        }

        if (mode.replace("-", "").length() != 3 | mode.replace("+", "").length() != 3) {
            this.errorTerminate("mode は 3 桁の数列で指定してください。");
            return permission;
        }

        int userMode = Integer.parseInt(mode.substring(0, 1));
        int groupMode = Integer.parseInt(mode.substring(1, 2));
        int othersMode = Integer.parseInt(mode.substring(2, 3));

        if (userMode > 7 | groupMode > 7 | othersMode > 7) {
            this.errorTerminate("mode は 0 から 7 までの整数で構成してください。");
            return permission;
        }

        String userPermission = this.toStringFromMode(userMode);
        String groupPermission = this.toStringFromMode(groupMode);
        String othersPermission = this.toStringFromMode(othersMode);

        String permString = userPermission + groupPermission + othersPermission;
        permission = PosixFilePermissions.fromString(permString);

        this.setCode(2);
        return permission;
    }

    /*
     * @see mode_resource.ModeResource#getMode(java.lang.String)
     */
    @Override
    public Set<PosixFilePermission> getMode(String path) {
        this.initStatus();

        Set<PosixFilePermission> permission = null;
        File f = new File(path);

        if (!f.exists()) {
            this.errorTerminate(path + " が見つかりません");
            return permission;
        }

        try {
            permission = Files.getPosixFilePermissions(f.toPath());
            this.setCode(2);
            return permission;
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。 " + e.toString());
            return permission;
        }
    }

    /*
     * @see mode_resource.ModeResource#setMode(java.lang.String, java.lang.String)
     */
    @Override
    public void setMode(String path, String mode) {
        this.initStatus();

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            this.errorTerminate(osName + " ではパーミッションの取得 / 設定はサポートしていません。");
            return;
        }

        Set<PosixFilePermission> curPermission = this.getMode(path);

        if (this.getCode() == 1) {
            return;
        }

        Set<PosixFilePermission> newPermission = this.createMode(mode);

        if (this.getCode() == 1) {
            return;
        }

        if (curPermission.equals(newPermission)) {
            this.initStatus();
            return;
        }

        System.out.println("パーミッションを変更します。");

        File f = new File(path);

        try {
            Files.setPosixFilePermissions(f.toPath(), newPermission);
            this.setCode(2);
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。 " + e.toString());
        }
    }

}
