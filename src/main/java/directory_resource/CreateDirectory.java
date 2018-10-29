package directory_resource;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import status_resource.StatusController;

/**
 * ディレクトリを新規作成する。
 */
@Service
public class CreateDirectory extends StatusController {
    @Autowired
    private DirectoryProperties dp;

    /**
     * @param path 作成対象とするディレクトリのパスを指定する。
     */
    public void init(String path) {
        dp.setPath(path);
    }

    /**
     * ディレクトリを新規作成する。
     */
    public void runCommand() {
        this.initStatus();

        File f = new File(dp.getPath());

        if (!f.isDirectory()) {
            System.out.println(dp.getPath() + " を作成します。");
            f.mkdirs();
            this.setCode(2);
        }
    }
}
