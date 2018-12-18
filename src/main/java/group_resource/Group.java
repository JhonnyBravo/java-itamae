package group_resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserPrincipalLookupService;

import status_resource.Status;

/**
 * @see group_resource.GroupResource
 */
public class Group extends Status implements GroupResource {

    /*
     * @see group_resource.GroupResource#createGroup(java.lang.String)
     */
    @Override
    public GroupPrincipal createGroup(String group) {
        this.initStatus();

        FileSystem fs = FileSystems.getDefault();
        UserPrincipalLookupService upls = fs.getUserPrincipalLookupService();
        GroupPrincipal gp = null;

        try {
            gp = upls.lookupPrincipalByGroupName(group);
            this.setCode(2);
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。 " + e.toString());
        }

        return gp;
    }

    /*
     * @see group_resource.GroupResource#getGroup(java.lang.String)
     */
    @Override
    public GroupPrincipal getGroup(String path) {
        this.initStatus();

        GroupPrincipal gp = null;
        File f = new File(path);

        if (!f.exists()) {
            this.errorTerminate(path + " が見つかりません。");
            return gp;
        }

        PosixFileAttributeView pfav = Files.getFileAttributeView(f.toPath(), PosixFileAttributeView.class);

        try {
            gp = pfav.readAttributes().group();
            this.setCode(2);
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。 " + e.toString());
        }

        return gp;
    }

    /*
     * @see group_resource.GroupResource#setGroup(java.lang.String,
     * java.lang.String)
     */
    @Override
    public void setGroup(String path, String group) {
        this.initStatus();

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            this.errorTerminate(osName + " ではグループ所有者の取得 / 設定はサポートしていません。");
            return;
        }

        GroupPrincipal curGp = this.getGroup(path);

        if (this.getCode() == 1) {
            return;
        }

        GroupPrincipal newGp = this.createGroup(group);

        if (this.getCode() == 1) {
            return;
        }

        if (curGp.equals(newGp)) {
            this.initStatus();
            return;
        }

        System.out.println("グループ所有者を変更します。");

        File f = new File(path);
        PosixFileAttributeView pfav = Files.getFileAttributeView(f.toPath(), PosixFileAttributeView.class);

        try {
            pfav.setGroup(newGp);
            this.setCode(2);
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。 " + e.toString());
        }
    }

}
