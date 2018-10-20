package file_resource;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import status_resource.StatusController;

/**
 * ファイルを削除する。
 */
@Service
public class DeleteFile extends StatusController {
    @Autowired
    private FileProperties fp;

    /**
     * @param path 削除対象とするファイルのパスを指定する。
     */
    public void init(String path) {
        fp.setPath(path);
    }

    /**
     * ファイルを削除する。
     */
    public void runCommand() {
        this.initStatus();

        File f = new File(fp.getPath());

        if (f.isFile()) {
            System.out.println(fp.getPath() + " を削除します。");
            f.delete();
            this.setCode(2);
        } else {
            return;
        }
    }

}
