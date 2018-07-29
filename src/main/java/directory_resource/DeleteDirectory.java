package directory_resource;

import java.io.File;

import status_resource.StatusController;

/**
 * ディレクトリを削除する。
 */
public class DeleteDirectory extends StatusController {
    private String path;

    /**
     * @param path 削除対象とするディレクトリのパスを指定する。
     */
    public DeleteDirectory(String path) {
        this.path = path;
    }

    /**
     * ディレクトリを再帰的に削除する。
     */
    public void runCommand() {
        this.initStatus();

        this.delete(new File(this.path));
        this.setCode(2);
    }

    /**
     * ディレクトリを再帰的に削除する。
     * 
     * @param directory 削除対象とするディレクトリを表す File オブジェクトを指定する。
     */
    private void delete(File directory) {
        for (File f : directory.listFiles()) {
            delete(f);
        }

        directory.delete();
    }
}
