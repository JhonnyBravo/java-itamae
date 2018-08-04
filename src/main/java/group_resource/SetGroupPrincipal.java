package group_resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;

import status_resource.StatusController;

/**
 * ファイルまたはディレクトリのグループ所有者を変更する。
 */
public class SetGroupPrincipal extends StatusController {
    private String path;
    private String group;

    /**
     * @param path  操作対象とするファイルまたはディレクトリのパスを指定する。
     * @param group 新しくグループ所有者として設定するグループの名前を指定する。
     */
    public SetGroupPrincipal(String path, String group) {
        this.path = path;
        this.group = group;
    }

    /**
     * ファイルまたはディレクトリのグループ所有者を変更する。
     */
    public void runCommand() {
        this.initStatus();

        // 現在のグループ所有者を取得する。
        GetGroupPrincipal ggp = new GetGroupPrincipal(this.path);
        GroupPrincipal curPrincipal = ggp.runCommand();

        if (ggp.getCode() == 1) {
            this.setCode(ggp.getCode());
            return;
        }

        // 新規設定するグループ所有者を取得する。
        CreateGroupPrincipal cgp = new CreateGroupPrincipal(this.group);
        GroupPrincipal newPrincipal = cgp.runCommand();

        if (cgp.getCode() == 1) {
            this.setCode(cgp.getCode());
            return;
        }

        // 現在のグループ所有者と新しいグループ所有者が同一である場合は何もせずに終了する。
        if (curPrincipal.equals(newPrincipal)) {
            this.initStatus();
            return;
        }

        // 新しいグループ所有者を設定する。
        Path p = new File(this.path).toPath();
        PosixFileAttributeView pfa = Files.getFileAttributeView(p, PosixFileAttributeView.class);

        try {
            pfa.setGroup(newPrincipal);
            this.setCode(2);
        } catch (IOException e) {
            this.setCode(1);
            this.setMessage("エラーが発生しました。 " + e.toString());
        }
    }
}
