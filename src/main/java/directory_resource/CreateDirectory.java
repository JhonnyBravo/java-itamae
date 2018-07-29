/**
 * 
 */
package directory_resource;

import java.io.File;

import status_resource.StatusController;

/**
 * ディレクトリを新規作成する。
 */
public class CreateDirectory extends StatusController {
    private String path;

    /**
     * @param path 作成対象とするディレクトリのパスを指定する。
     */
    public CreateDirectory(String path) {
        this.path = path;
    }

    /**
     * ディレクトリを新規作成する。
     */
    public void runCommand() {
        this.initStatus();

        new File(this.path).mkdirs();
        this.setCode(2);
    }
}
