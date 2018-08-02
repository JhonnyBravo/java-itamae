package owner_resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.UserPrincipal;

import status_resource.StatusController;

/**
 * ファイルまたはディレクトリに現在設定されている所有者を表す UserPrincipal オブジェクトを生成する。
 */
public class GetUserPrincipal extends StatusController {
    private String path;

    /**
     * @param path 操作対象とするファイルまたはディレクトリのパスを指定する。
     */
    public GetUserPrincipal(String path) {
        this.path = path;
    }

    /**
     * ファイルまたはディレクトリに現在設定されている所有者を表す UserPrincipal オブジェクトを返す。
     * 
     * @return UserPrincipal
     */
    public UserPrincipal runCommand() {
        this.initStatus();

        UserPrincipal up = null;

        try {
            up = Files.getOwner(new File(this.path).toPath());
            this.setCode(2);
        } catch (IOException e) {
            this.setCode(1);
            this.setMessage("エラーが発生しました。 " + e.toString());
            this.errorTerminate();
        }

        return up;
    }
}
