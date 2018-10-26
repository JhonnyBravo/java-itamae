package group_resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;

import status_resource.StatusController;

/**
 * ファイルまたはディレクトリに現在設定されている GroupPrincipal オブジェクト(グループ所有者を表すオブジェクト)を取得する。
 */
public class GetGroupPrincipal extends StatusController {
    private GroupProperties properties = new GroupProperties();

    /**
     * @param path 操作対象とするファイルまたはディレクトリのパスを指定する。
     */
    public void init(String path) {
        properties.setPath(path);
    }

    /**
     * ファイルまたはディレクトリに現在設定されている GroupPrincipal オブジェクト(グループ所有者を表すオブジェクト)を取得する。
     * 
     * @return GroupPrincipal
     */
    public GroupPrincipal runCommand() {
        this.initStatus();

        GroupPrincipal principal = null;
        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            this.setMessage(osName + " ではグループ所有者の取得 / 設定はできません。");
            this.errorTerminate();
            return principal;
        }

        File f = new File(properties.getPath());

        if (!f.exists()) {
            this.setMessage(properties.getPath() + " が見つかりません。");
            this.errorTerminate();
            return principal;
        }

        PosixFileAttributeView pfa = Files.getFileAttributeView(f.toPath(), PosixFileAttributeView.class);

        try {
            principal = pfa.readAttributes().group();
            this.setCode(2);
        } catch (IOException e) {
            this.setMessage("エラーが発生しました。 " + e.toString());
            this.errorTerminate();
        }

        return principal;
    }
}
