package group_resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import status_resource.StatusController;

/**
 * ファイルまたはディレクトリのグループ所有者を変更する。
 */
@Service
public class SetGroupPrincipal extends StatusController {
    @Autowired
    private GroupProperties properties;

    @Autowired
    private GetGroupPrincipal ggp;

    @Autowired
    private CreateGroupPrincipal cgp;

    /**
     * @param path  操作対象とするファイルまたはディレクトリのパスを指定する。
     * @param group 新しくグループ所有者として設定するグループの名前を指定する。
     */
    public void init(String path, String group) {
        properties.setPath(path);
        properties.setGroup(group);
    }

    /**
     * ファイルまたはディレクトリのグループ所有者を変更する。
     */
    public void runCommand() {
        this.initStatus();

        // 現在のグループ所有者を取得する。
        ggp.init(properties.getPath());
        GroupPrincipal curPrincipal = ggp.runCommand();

        if (ggp.getCode() == 1) {
            this.setCode(ggp.getCode());
            return;
        }

        // 新規設定するグループ所有者を取得する。
        cgp.init(properties.getGroup());
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
        System.out.println("グループ所有者を変更します。");
        Path p = new File(properties.getPath()).toPath();
        PosixFileAttributeView pfa = Files.getFileAttributeView(p, PosixFileAttributeView.class);

        try {
            pfa.setGroup(newPrincipal);
            this.setCode(2);
        } catch (IOException e) {
            this.setMessage("エラーが発生しました。 " + e.toString());
            this.errorTerminate();
        }
    }
}
