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
    private OwnerProperties op = new OwnerProperties();
    private GetUserPrincipal gup = new GetUserPrincipal();
    private CreateUserPrincipal cup = new CreateUserPrincipal();

    /**
     * @param path  操作対象とするファイルまたはディレクトリのパスを指定する。
     * @param owner 新しく所有者として設定するユーザの名前を指定する。
     */
    public void init(String path, String owner) {
        op.setPath(path);
        op.setOwner(owner);
    }

    /**
     * ファイルまたはディレクトリの所有者を変更する。
     */
    public void runCommand() {
        this.initStatus();

        // 現在のファイル所有者を取得する。
        gup.init(op.getPath());
        UserPrincipal curPrincipal = gup.runCommand();

        if (gup.getCode() == 1) {
            this.setCode(gup.getCode());
            return;
        }

        // 新規設定するファイル所有者を取得する。
        cup.init(op.getOwner());
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
        System.out.println("所有者を変更します。");
        Path p = new File(op.getPath()).toPath();

        try {
            Files.setOwner(p, newPrincipal);
            this.setCode(2);
        } catch (IOException e) {
            this.setMessage("エラーが発生しました。 " + e.toString());
            this.errorTerminate();
        }
    }
}
