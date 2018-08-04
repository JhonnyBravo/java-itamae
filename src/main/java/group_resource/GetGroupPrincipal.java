package group_resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;

import status_resource.StatusController;

/**
 * ファイルまたはディレクトリに現在設定されているグループ所有者を表す GroupPrincipal オブジェクトを生成する。
 */
public class GetGroupPrincipal extends StatusController {
    private String path;

    /**
     * @param path 操作対象とするファイルまたはディレクトリのパスを指定する。
     */
    public GetGroupPrincipal(String path) {
        this.path = path;
    }

    /**
     * ファイルまたはディレクトリに現在設定されているグループ所有者を表す GroupPrincipal オブジェクトを返す。
     * 
     * @return GroupPrincipal
     */
    public GroupPrincipal runCommand() {
        this.initStatus();

        GroupPrincipal up = null;
        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            this.setCode(1);
            this.setMessage(osName + " ではグループ所有者の取得 / 設定はできません。");
            this.errorTerminate();
            return up;
        }

        PosixFileAttributeView pfa = Files.getFileAttributeView(new File(this.path).toPath(),
                PosixFileAttributeView.class);

        try {
            up = pfa.readAttributes().group();
            this.setCode(2);
        } catch (IOException e) {
            this.setCode(1);
            this.setMessage("エラーが発生しました。 " + e.toString());
            this.errorTerminate();
        }

        return up;
    }
}
