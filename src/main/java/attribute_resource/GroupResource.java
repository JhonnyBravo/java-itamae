package attribute_resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserPrincipalLookupService;

/**
 * ファイルまたはディレクトリのグループ所有者を管理する。
 */
public class GroupResource extends AttributeResource {
    private String path;
    private String group;
    private File file;

    /**
     * @param path  操作対象とするファイルまたはディレクトリのパスを指定する。
     * @param group 新しいグループ所有者として設定するグループ名を指定する。
     */
    public GroupResource(String path, String group) {
        this.path = path;
        this.group = group;
        this.file = new File(path);
    }

    /**
     * @return Object ファイルまたはディレクトリに現在のグループ所有者として設定されている GroupPrincipal を取得する。
     * 
     * @see attribute_resource.AttributeResource#getAttribute()
     */
    @Override
    protected Object getAttribute() {
        this.initStatus();

        GroupPrincipal gp = null;

        if (!this.file.exists()) {
            this.errorTerminate(this.path + " が見つかりません。");
            return gp;
        }

        PosixFileAttributeView pfav = Files.getFileAttributeView(this.file.toPath(), PosixFileAttributeView.class);

        try {
            gp = pfav.readAttributes().group();
            this.setCode(2);
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。 " + e);
        }

        return gp;
    }

    /**
     * @return Object 新規生成された GroupPrincipal を返す。
     * 
     * @see attribute_resource.AttributeResource#createAttribute()
     */
    @Override
    protected Object createAttribute() {
        this.initStatus();

        FileSystem fs = FileSystems.getDefault();
        UserPrincipalLookupService upls = fs.getUserPrincipalLookupService();
        GroupPrincipal gp = null;

        try {
            gp = upls.lookupPrincipalByGroupName(this.group);
            this.setCode(2);
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。 " + e);
        }

        return gp;
    }

    /**
     * ファイルまたはディレクトリのグループ所有者を変更する。
     * 
     * @see attribute_resource.AttributeResource#setAttribute()
     */
    @Override
    public void setAttribute() {
        this.initStatus();

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            this.errorTerminate(osName + " ではグループ所有者の取得 / 設定はサポートしていません。");
            return;
        }

        GroupPrincipal curGroup = (GroupPrincipal) this.getAttribute();

        if (this.getCode() == 1) {
            return;
        }

        GroupPrincipal newGroup = (GroupPrincipal) this.createAttribute();

        if (this.getCode() == 1) {
            return;
        }

        if (curGroup.equals(newGroup)) {
            this.initStatus();
            return;
        }

        System.out.println("グループ所有者を変更します。");

        PosixFileAttributeView pfav = Files.getFileAttributeView(this.file.toPath(), PosixFileAttributeView.class);

        try {
            pfav.setGroup(newGroup);
            this.setCode(2);
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。 " + e);
        }
    }

}
