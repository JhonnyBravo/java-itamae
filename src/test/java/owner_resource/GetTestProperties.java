package owner_resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import status_resource.StatusController;

/**
 * テスト時に使用するプロパティを外部ファイルから取得する。
 */
public class GetTestProperties extends StatusController {
    // GroupProperties gp = new GroupProperties();
    private OwnerProperties op = new OwnerProperties();
    private Properties p = new Properties();

    /**
     * @param path プロパティ取得対象とするファイルのパスを指定する。
     */
    public void init(String path) {
        op.setPath(path);
    }

    /**
     * @param path InputStream 取得対象とするファイルのパスを指定する。
     * @return InputStream
     */
    private InputStream getInputStream(String path) {
        this.initStatus();

        InputStream input = null;

        try {
            input = new FileInputStream(path);
            this.setCode(2);
            return input;
        } catch (FileNotFoundException e) {
            this.setMessage("エラーが発生しました。 " + e.toString());
            this.errorTerminate();
            return input;
        }
    }

    /**
     * @return ownerName テスト時に使用するユーザー名を返す。
     */
    public String getOwnerName() {
        this.initStatus();

        String ownerName = null;
        InputStream input = getInputStream(op.getPath());

        if (this.getCode() == 1) {
            return ownerName;
        }

        try {
            p.load(input);
            ownerName = p.getProperty("ownerName");
        } catch (IOException e) {
            this.setMessage("エラーが発生しました。 " + e.toString());
            this.errorTerminate();
            return ownerName;
        }

        if (ownerName == null) {
            this.setMessage("ownerName プロパティが見つかりません。");
            this.errorTerminate();
            return ownerName;
        }

        this.setCode(2);
        return ownerName;
    }
}
