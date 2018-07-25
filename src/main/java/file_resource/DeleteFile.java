package file_resource;

import java.io.File;

/**
 * ファイルを削除する。
 */
public class DeleteFile extends StatusController {
    private String path;

    /**
     * @param path 削除対象とするファイルのパスを指定する。
     */
    public DeleteFile(String path) {
        this.path = path;
    }

    /**
     * ファイルを削除する。
     */
    public void runCommand() {
        this.initStatus();

        new File(this.path).delete();
        this.setCode(2);
    }

}
