package basic_action_resource;

import java.io.File;
import java.io.FilenameFilter;

import attribute_resource.AttributeResource;
import attribute_resource.GroupResource;
import attribute_resource.ModeResource;
import attribute_resource.OwnerResource;

/**
 * ディレクトリを作成または削除する。
 */
public class DirectoryResource extends ActionResource {
    private String path;
    private File file;
    private AttributeResource attribute;
    private FilenameFilter filter;

    /**
     * @param path 操作対象とするディレクトリのパスを指定する。
     */
    public DirectoryResource(String path) {
        this.path = path;
        this.file = new File(path);
        this.filter = null;
    }

    /**
     * ディレクトリを作成する。
     * 
     * @see basic_action_resource.ActionResource#create()
     */
    @Override
    public void create() {
        this.initStatus();

        if (!this.file.isDirectory()) {
            System.out.println(this.path + " を作成します。");
            this.file.mkdirs();
            this.setCode(2);
        }
    }

    /**
     * ディレクトリを削除する。
     * 
     * @see basic_action_resource.ActionResource#delete()
     */
    @Override
    public void delete() {
        this.initStatus();

        if (this.file.isDirectory()) {
            System.out.println(this.path + " を削除します。");
            this.deleteDirectory(this.file);
            this.setCode(2);
        }
    }

    /**
     * ディレクトリを再帰的に削除する。
     * 
     * @param directory 削除対象とするディレクトリの File オブジェクトを指定する。
     */
    private void deleteDirectory(File directory) {
        for (File f : directory.listFiles()) {
            deleteDirectory(f);
        }

        directory.delete();
    }

    @Override
    public void setOwner(String owner) {
        this.initStatus();

        if (!this.file.isDirectory()) {
            this.errorTerminate(this.path + " はディレクトリではありません。");
            return;
        }

        attribute = new OwnerResource(this.path, owner);
        attribute.setAttribute();
        this.setCode(attribute.getCode());
    }

    @Override
    public void setGroup(String group) {
        this.initStatus();

        if (!this.file.isDirectory()) {
            this.errorTerminate(this.path + " はディレクトリではありません。");
            return;
        }

        attribute = new GroupResource(this.path, group);
        attribute.setAttribute();
        this.setCode(attribute.getCode());
    }

    @Override
    public void setMode(String mode) {
        this.initStatus();

        if (!this.file.isDirectory()) {
            this.errorTerminate(this.path + " はディレクトリではありません。");
            return;
        }

        attribute = new ModeResource(this.path, mode);
        attribute.setAttribute();
        this.setCode(attribute.getCode());
    }

    /**
     * @param extension 取得対象とするファイルの拡張子を指定する。
     */
    public void setFileFilter(String extension) {
        this.filter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                if (name.toLowerCase().endsWith(extension)) {
                    return true;
                } else {
                    return false;
                }
            }
        };
    }

    /**
     * @return ディレクトリの直下に存在するファイルの一覧を取得して返す。
     */
    public File[] getFiles() {
        File[] result = null;

        this.initStatus();

        if (!this.file.isDirectory()) {
            this.errorTerminate(this.path + " が見つかりません。");
            return result;
        }

        if (this.filter == null) {
            result = this.file.listFiles();
        } else {
            result = this.file.listFiles(filter);
        }

        if (result.length > 0) {
            this.setCode(2);
        }

        return result;
    }
}
