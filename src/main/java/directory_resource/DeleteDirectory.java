package directory_resource;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import status_resource.StatusController;

/**
 * ディレクトリを削除する。
 */
@Service
public class DeleteDirectory extends StatusController {
    @Autowired
    private DirectoryProperties dp;

    /**
     * @param path 削除対象とするディレクトリのパスを指定する。
     */
    public void init(String path) {
        dp.setPath(path);
    }

    /**
     * ディレクトリを再帰的に削除する。
     */
    public void runCommand() {
        this.initStatus();

        File f = new File(dp.getPath());

        if (f.isDirectory()) {
            System.out.println(dp.getPath() + " を削除します。");
            this.delete(f);
            this.setCode(2);
        }
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
