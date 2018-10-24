package owner_resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import status_resource.StatusController;

/**
 * ファイルまたはディレクトリに現在設定されている UserPrincipal オブジェクト(所有者を表すオブジェクト)を取得する。
 */
@Service
public class GetUserPrincipal extends StatusController {
    @Autowired
    private OwnerProperties op;

    /**
     * @param path 操作対象とするファイルまたはディレクトリのパスを指定する。
     */
    public void init(String path) {
        op.setPath(path);
    }

    /**
     * ファイルまたはディレクトリに現在設定されている UserPrincipal オブジェクト(所有者を表すオブジェクト)を取得する。
     * 
     * @return UserPrincipal
     */
    public UserPrincipal runCommand() {
        this.initStatus();

        UserPrincipal up = null;
        File f = new File(op.getPath());

        if (!f.exists()) {
            this.setMessage(op.getPath() + " が見つかりません。");
            this.errorTerminate();
            return up;
        }

        try {
            up = Files.getOwner(f.toPath());
            this.setCode(2);
        } catch (IOException e) {
            this.setMessage("エラーが発生しました。 " + e.toString());
            this.errorTerminate();
        }

        return up;
    }
}
