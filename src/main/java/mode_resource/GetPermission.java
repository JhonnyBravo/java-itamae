package mode_resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

import status_resource.StatusController;

/**
 * ファイルまたはディレクトリに現在設定されている PosixFilePermission オブジェクトを取得する。
 */
public class GetPermission extends StatusController {
    private String path;

    /**
     * @param path 操作対象とするファイルまたはディレクトリのパスを指定する。
     */
    public GetPermission(String path) {
        this.path = path;
    }

    /**
     * ファイルまたはディレクトリに現在設定されている PosixFilePermission オブジェクトを返す。
     * 
     * @return GroupPrincipal
     */
    public Set<PosixFilePermission> runCommand() {
        this.initStatus();

        Set<PosixFilePermission> permission = null;
        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            this.setCode(1);
            this.setMessage(osName + " ではアクセス権限の取得 / 設定はできません。");
            this.errorTerminate();
            return permission;
        }

        try {
            permission = Files.getPosixFilePermissions(new File(this.path).toPath());
            this.setCode(2);
            return permission;
        } catch (IOException e) {
            this.setCode(1);
            this.setMessage("エラーが発生しました。 " + e.toString());
            return permission;
        }
    }
}
