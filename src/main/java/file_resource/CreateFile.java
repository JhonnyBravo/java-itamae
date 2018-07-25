package file_resource;

import java.io.File;
import java.io.IOException;

/**
 * ファイルを作成する。
 */
public class CreateFile extends StatusController {
    private String path;

    /**
     * @param path 作成対象とするファイルのパスを指定する。
     */
    public CreateFile(String path) {
        this.path = path;
    }

    /**
     * ファイルを作成する。
     */
    public void runCommand() {
        this.initStatus();

        try {
            new File(this.path).createNewFile();
            this.setCode(2);
        } catch (IOException e) {
            this.setMessage("エラーが発生しました。" + e.toString());
            this.errorTerminate();
        }
    }

}
