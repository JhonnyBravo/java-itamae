package basic_action_resource;

import java.io.File;
import java.io.IOException;

import attribute_resource.AttributeResource;
import attribute_resource.GroupResource;
import attribute_resource.ModeResource;
import attribute_resource.OwnerResource;

/**
 * ファイルを作成または削除する。
 */
public class FileResource extends ActionResource {
    private String path;
    private File file;
    private AttributeResource attribute;

    /**
     * @param path 操作対象とするファイルのパスを指定する。
     */
    public FileResource(String path) {
        this.path = path;
        this.file = new File(path);
    }

    /**
     * ファイルを新規作成する。
     * 
     * @see basic_action_resource.ActionResource#create()
     */
    @Override
    public void create() {
        this.initStatus();

        if (!this.file.isFile()) {
            System.out.println(this.path + " を作成します。");

            try {
                this.file.createNewFile();
                this.setCode(2);
            } catch (IOException e) {
                this.errorTerminate("エラーが発生しました。" + e);
            }
        }
    }

    /**
     * ファイルを削除する。
     * 
     * @see basic_action_resource.ActionResource#delete()
     */
    @Override
    public void delete() {
        this.initStatus();

        if (this.file.isFile()) {
            System.out.println(this.path + " を削除します。");
            this.file.delete();
            this.setCode(2);
        }
    }

    @Override
    public void setOwner(String owner) {
        this.initStatus();

        if (!this.file.isFile()) {
            this.errorTerminate(this.path + " はファイルではありません。");
            return;
        }

        attribute = new OwnerResource(this.path, owner);
        attribute.setAttribute();
        this.setCode(attribute.getCode());
    }

    @Override
    public void setGroup(String group) {
        this.initStatus();

        if (!this.file.isFile()) {
            this.errorTerminate(this.path + " はファイルではありません。");
            return;
        }

        attribute = new GroupResource(this.path, group);
        attribute.setAttribute();
        this.setCode(attribute.getCode());
    }

    @Override
    public void setMode(String mode) {
        this.initStatus();

        if (!this.file.isFile()) {
            this.errorTerminate(this.path + " はファイルではありません。");
            return;
        }

        attribute = new ModeResource(this.path, mode);
        attribute.setAttribute();
        this.setCode(attribute.getCode());
    }

}
