package mode_resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import status_resource.StatusController;

/**
 * ファイルまたはディレクトリに現在設定されている PosixFilePermission オブジェクトを取得する。
 */
@Service
public class GetPermission extends StatusController {
    @Autowired
    private ModeProperties mp;

    /**
     * @param path 操作対象とするファイルまたはディレクトリのパスを指定する。
     */
    public void init(String path) {
        mp.setPath(path);
    }

    /**
     * ファイルまたはディレクトリに現在設定されている PosixFilePermission オブジェクトを返す。
     * 
     * @return Set&lt;PosixFilePermission&gt;
     */
    public Set<PosixFilePermission> runCommand() {
        this.initStatus();

        Set<PosixFilePermission> permission = null;
        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            this.setMessage(osName + " ではアクセス権限の取得 / 設定はできません。");
            this.errorTerminate();
            return permission;
        }

        File f = new File(mp.getPath());

        if (!f.exists()) {
            this.setMessage(mp.getPath() + " が見つかりません。");
            this.errorTerminate();
            return permission;
        }

        try {
            permission = Files.getPosixFilePermissions(f.toPath());
            this.setCode(2);
            return permission;
        } catch (IOException e) {
            this.setMessage("エラーが発生しました。 " + e.toString());
            return permission;
        }
    }
}
