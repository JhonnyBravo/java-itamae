package attribute_resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;

/**
 * ファイルまたはディレクトリの所有者を管理する。
 */
public class OwnerResource extends AttributeResource {
    private String path;
    private String owner;
    private File file;

    /**
     * @param path  操作対象とするファイルまたはディレクトリのパスを指定する。
     * @param owner 新しい所有者として設定するユーザ名を指定する。
     */
    public OwnerResource(String path, String owner) {
        this.path = path;
        this.file = new File(path);
        this.owner = owner;
    }

    /**
     * @return Object ファイルまたはディレクトリに現在の所有者として設定されている UserPrincipal を返す。
     * 
     * @see attribute_resource.AttributeResource#getAttribute()
     */
    @Override
    protected Object getAttribute() {
        this.initStatus();

        UserPrincipal up = null;

        if (!this.file.exists()) {
            this.errorTerminate(this.path + " が見つかりません。");
            return up;
        }

        try {
            up = Files.getOwner(this.file.toPath());
            this.setCode(2);
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。 " + e);
        }

        return up;
    }

    /**
     * @return Object 新規生成された UserPrincipal を返す。
     * 
     * @see attribute_resource.AttributeResource#createAttribute()
     */
    @Override
    protected Object createAttribute() {
        this.initStatus();

        FileSystem fs = FileSystems.getDefault();
        UserPrincipalLookupService upls = fs.getUserPrincipalLookupService();
        UserPrincipal up = null;

        try {
            up = upls.lookupPrincipalByName(this.owner);
            this.setCode(2);
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。 " + e);
        }

        return up;
    }

    /**
     * ファイルまたはディレクトリの所有者を変更する。
     * 
     * @see attribute_resource.AttributeResource#setAttribute()
     */
    @Override
    public void setAttribute() {
        this.initStatus();

        UserPrincipal curOwner = (UserPrincipal) this.getAttribute();

        if (this.getCode() == 1) {
            return;
        }

        UserPrincipal newOwner = (UserPrincipal) this.createAttribute();

        if (this.getCode() == 1) {
            return;
        }

        if (curOwner.equals(newOwner)) {
            this.initStatus();
            return;
        }

        System.out.println("所有者を変更します。");

        try {
            Files.setOwner(this.file.toPath(), newOwner);
            this.setCode(2);
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。" + e);
        }
    }

}
