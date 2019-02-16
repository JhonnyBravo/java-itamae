package basic_action_resource;

import java.io.File;
import java.io.IOException;

/**
 * ファイルを作成または削除する。
 */
public class FileResource extends ActionResource {
    private String path;
    private File file;

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

}
