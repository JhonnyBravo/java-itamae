package owner_resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.UserPrincipal;

import status_resource.StatusController;

/**
 * ファイルまたはディレクトリの所有者を変更する。
 */
public class SetUserPrincipal extends StatusController {
    private String path;
    private String owner;

    /**
     * @param path  操作対象とするファイルまたはディレクトリのパスを指定する。
     * @param owner 新しく所有者として設定するユーザの名前を指定する。
     */
    public SetUserPrincipal(String path, String owner) {
        this.path = path;
        this.owner = owner;
    }

    /**
     * ファイルまたはディレクトリの所有者を変更する。
     */
    public void runCommand() {
        this.initStatus();

        // 現在のファイル所有者を取得する。
        GetUserPrincipal gup = new GetUserPrincipal(this.path);
        UserPrincipal curPrincipal = gup.runCommand();

        if (gup.getCode() == 1) {
            this.setCode(gup.getCode());
            return;
        }

        // 新規設定するファイル所有者を取得する。
        CreateUserPrincipal cup = new CreateUserPrincipal(this.owner);
        UserPrincipal newPrincipal = cup.runCommand();

        if (cup.getCode() == 1) {
            this.setCode(cup.getCode());
            return;
        }

        // 現在の所有者と新しい所有者が同一である場合は何もせずに終了する。
        if (curPrincipal.equals(newPrincipal)) {
            this.initStatus();
            return;
        }

        // 新しいファイル所有者を設定する。
        Path p = new File(this.path).toPath();

        try {
            Files.setOwner(p, newPrincipal);
            this.setCode(2);
        } catch (IOException e) {
            this.setCode(1);
            this.setMessage("エラーが発生しました。 " + e.toString());
        }
    }
}
