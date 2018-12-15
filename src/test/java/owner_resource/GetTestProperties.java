package owner_resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import status_resource.Status;

/**
 * テスト時に使用するプロパティを外部ファイルから取得する。
 */
public class GetTestProperties extends Status {
    private Properties p = new Properties();

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
            this.errorTerminate("エラーが発生しました。 " + e.toString());
            return input;
        }
    }

    /**
     * プロパティファイルからテストに使用するユーザ名を取得する。
     * 
     * @param path 読込対象とするファイルのパスを指定する。
     * @return ownerName テスト時に使用するユーザー名を返す。
     */
    public String getOwnerName(String path) {
        this.initStatus();

        String ownerName = null;
        InputStream input = getInputStream(path);

        if (this.getCode() == 1) {
            return ownerName;
        }

        try {
            p.load(input);
            ownerName = p.getProperty("ownerName");
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。 " + e.toString());
            return ownerName;
        }

        if (ownerName == null) {
            this.errorTerminate("ownerName プロパティが見つかりません。");
            return ownerName;
        }

        this.setCode(2);
        return ownerName;
    }
}
