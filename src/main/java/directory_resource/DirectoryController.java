package directory_resource;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import group_resource.Group;
import mode_resource.Mode;
import owner_resource.Owner;
import status_resource.Status;

/**
 * @see directory_resource.DirectoryResource
 */
@Service
@Import({ Owner.class, Group.class, Mode.class })
public class DirectoryController extends Status implements DirectoryResource {
    private String path = null;
    private File file = null;

    @Autowired
    private Owner ownerResource;

    @Autowired
    private Group groupResource;

    @Autowired
    private Mode modeResource;

    /**
     * @see directory_resource.DirectoryResource#setOwner(String)
     */
    @Override
    public void setOwner(String owner) {
        this.initStatus();

        if (this.path == null) {
            this.errorTerminate("path を指定してください。");
            return;
        }

        if (!this.file.isDirectory()) {
            this.errorTerminate(this.path + " が見つかりません。");
            return;
        }

        ownerResource.setOwner(this.path, owner);
        this.setCode(ownerResource.getCode());
    }

    /**
     * @see directory_resource.DirectoryResource#setGroup(String)
     */
    @Override
    public void setGroup(String group) {
        this.initStatus();

        if (this.path == null) {
            this.errorTerminate("path を指定してください。");
            return;
        }

        if (!this.file.isDirectory()) {
            this.errorTerminate(this.path + " が見つかりません。");
            return;
        }

        groupResource.setGroup(this.path, group);
        this.setCode(groupResource.getCode());
    }

    /**
     * @see directory_resource.DirectoryResource#setMode(String)
     */
    @Override
    public void setMode(String mode) {
        this.initStatus();

        if (this.path == null) {
            this.errorTerminate("path を指定してください。");
            return;
        }

        if (!this.file.isDirectory()) {
            this.errorTerminate(this.path + " が見つかりません。");
            return;
        }

        modeResource.setMode(this.path, mode);
        this.setCode(modeResource.getCode());
    }

    /**
     * @see directory_resource.DirectoryResource#setPath(String)
     */
    @Override
    public void setPath(String path) {
        this.path = path;
        this.file = new File(path);
    }

    /**
     * @see directory_resource.DirectoryResource#createDirectory()
     */
    @Override
    public void createDirectory() {
        this.initStatus();

        if (this.path == null) {
            this.errorTerminate("path を指定してください。");
            return;
        }

        if (!this.file.isDirectory()) {
            System.out.println(this.path + " を作成します。");
            this.file.mkdirs();
            this.setCode(2);
        }
    }

    /**
     * ディレクトリを再帰的に削除する。
     * 
     * @param directory 削除対象とするディレクトリを指定する。
     */
    private void delete(File directory) {
        for (File f : directory.listFiles()) {
            delete(f);
        }

        directory.delete();
    }

    /**
     * @see directory_resource.DirectoryResource#deleteDirectory()
     */
    @Override
    public void deleteDirectory() {
        this.initStatus();

        if (this.path == null) {
            this.errorTerminate("path を指定してください。");
            return;
        }

        if (!this.file.isDirectory()) {
            this.initStatus();
            return;
        } else {
            System.out.println(this.path + " を削除します。");
            this.delete(this.file);
            this.setCode(2);
        }
    }
}
