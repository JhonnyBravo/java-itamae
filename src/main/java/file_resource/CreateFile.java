package file_resource;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import status_resource.StatusController;

/**
 * ファイルを作成する。
 */
@Service
public class CreateFile extends StatusController {
    @Autowired
    private FileProperties fp;

    /**
     * @param path 作成対象とするファイルのパスを指定する。
     */
    public void init(String path) {
        fp.setPath(path);
    }

    /**
     * ファイルを作成する。
     */
    public void runCommand() {
        this.initStatus();

        File f = new File(fp.getPath());

        if (f.isFile()) {
            return;
        } else {
            System.out.println(fp.getPath() + " を作成します。");
        }

        try {
            f.createNewFile();
            this.setCode(2);
        } catch (IOException e) {
            this.setMessage("エラーが発生しました。" + e.toString());
            this.errorTerminate();
        }
    }

}
