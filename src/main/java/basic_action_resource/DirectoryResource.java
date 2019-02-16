package basic_action_resource;

import java.io.File;

/**
 * ディレクトリを作成または削除する。
 */
public class DirectoryResource extends ActionResource {
    private String path;
    private File file;

    /**
     * @param path 操作対象とするディレクトリのパスを指定する。
     */
    public DirectoryResource(String path) {
        this.path = path;
        this.file = new File(path);
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

}
