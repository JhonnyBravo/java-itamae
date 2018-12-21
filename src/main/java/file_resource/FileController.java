package file_resource;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import group_resource.Group;
import mode_resource.Mode;
import owner_resource.Owner;
import status_resource.Status;

/**
 * @see file_resource.FileResource
 */
@Service
@Import({ Group.class, Mode.class, Owner.class })
public class FileController extends Status implements FileResource {
    private String path = null;
    private File file = null;

    @Autowired
    private Owner ownerResource;

    @Autowired
    private Group groupResource;

    @Autowired
    private Mode modeResource;

    /**
     * @see file_resource.FileResource#setOwner(String)
     */
    @Override
    public void setOwner(String owner) {
        this.initStatus();

        if (this.path == null) {
            this.errorTerminate("path を指定してください。");
            return;
        }

        if (!this.file.isFile()) {
            this.errorTerminate(this.path + " が見つかりません。");
            return;
        }

        ownerResource.setOwner(this.path, owner);
        this.setCode(ownerResource.getCode());
    }

    /**
     * @see file_resource.FileResource#setGroup(String)
     */
    @Override
    public void setGroup(String group) {
        this.initStatus();

        if (this.path == null) {
            this.errorTerminate("path を指定してください。");
            return;
        }

        if (!this.file.isFile()) {
            this.errorTerminate(this.path + " が見つかりません。");
            return;
        }

        groupResource.setGroup(this.path, group);
        this.setCode(groupResource.getCode());
    }

    /**
     * @see file_resource.FileResource#setMode(String)
     */
    @Override
    public void setMode(String mode) {
        this.initStatus();

        if (this.path == null) {
            this.errorTerminate("path を指定してください。");
            return;
        }

        if (!this.file.isFile()) {
            this.errorTerminate(this.path + " が見つかりません。");
            return;
        }

        modeResource.setMode(this.path, mode);
        this.setCode(modeResource.getCode());
    }

    /**
     * @see file_resource.FileResource#setPath(String)
     */
    @Override
    public void setPath(String path) {
        this.path = path;
        this.file = new File(this.path);
    }

    /**
     * @see file_resource.FileResource#createFile()
     */
    @Override
    public void createFile() {
        this.initStatus();

        if (this.path == null) {
            this.errorTerminate("path を指定してください。");
            return;
        }

        if (this.file.isFile()) {
            this.initStatus();
            return;
        } else {
            System.out.println(this.path + " を作成します。");

            try {
                this.file.createNewFile();
                this.setCode(2);
            } catch (IOException e) {
                this.errorTerminate("エラーが発生しました。 " + e.toString());
            }
        }
    }

    /**
     * @see file_resource.FileResource#deleteFile()
     */
    @Override
    public void deleteFile() {
        this.initStatus();

        if (this.path == null) {
            this.errorTerminate("path を指定してください。");
            return;
        }

        if (!this.file.isFile()) {
            this.initStatus();
            return;
        } else {
            System.out.println(this.path + " を削除します。");
            this.file.delete();
            this.setCode(2);
        }
    }
}
