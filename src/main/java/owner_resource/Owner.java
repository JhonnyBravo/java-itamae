package owner_resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;

import status_resource.Status;

/**
 * @see owner_resource.OwnerResource
 */
public class Owner extends Status implements OwnerResource {

    /*
     * @see owner_resource.OwnerResource#createOwner(java.lang.String)
     */
    @Override
    public UserPrincipal createOwner(String owner) {
        this.initStatus();

        FileSystem fs = FileSystems.getDefault();
        UserPrincipalLookupService upls = fs.getUserPrincipalLookupService();
        UserPrincipal up = null;

        try {
            up = upls.lookupPrincipalByName(owner);
            this.setCode(2);
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。 " + e.toString());
        }

        return up;
    }

    /*
     * @see owner_resource.OwnerResource#getOwner(java.lang.String)
     */
    @Override
    public UserPrincipal getOwner(String path) {
        this.initStatus();

        UserPrincipal up = null;
        File f = new File(path);

        if (!f.exists()) {
            this.errorTerminate(path + " が見つかりません。");
            return up;
        }

        try {
            up = Files.getOwner(f.toPath());
            this.setCode(2);
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。" + e.toString());
        }

        return up;
    }

    /*
     * @see owner_resource.OwnerResource#setOwner(java.lang.String,
     * java.lang.String)
     */
    @Override
    public void setOwner(String path, String owner) {
        this.initStatus();

        UserPrincipal curUp = this.getOwner(path);

        if (this.getCode() == 1) {
            return;
        }

        UserPrincipal newUp = this.createOwner(owner);

        if (this.getCode() == 1) {
            return;
        }

        if (curUp.equals(newUp)) {
            this.initStatus();
            return;
        }

        System.out.println("所有者を変更します。");
        File f = new File(path);

        try {
            Files.setOwner(f.toPath(), newUp);
            this.setCode(2);
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。 " + e.toString());
        }
    }

}
