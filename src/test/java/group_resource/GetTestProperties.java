package group_resource;

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
    GroupProperties gp = new GroupProperties();
    Properties p = new Properties();

    /**
     * @param path プロパティ取得対象とするファイルのパスを指定する。
     */
    public void init(String path) {
        gp.setPath(path);
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
     * @return groupName テスト時に使用するグループ名を返す。
     */
    public String getGroupName() {
        this.initStatus();

        String groupName = null;
        InputStream input = getInputStream(gp.getPath());

        if (this.getCode() == 1) {
            return groupName;
        }

        try {
            p.load(input);
            groupName = p.getProperty("groupName");
        } catch (IOException e) {
            this.setMessage("エラーが発生しました。 " + e.toString());
            this.errorTerminate();
            return groupName;
        }

        if (groupName == null) {
            this.setMessage("groupName プロパティが見つかりません。");
            this.errorTerminate();
            return groupName;
        }

        this.setCode(2);
        return groupName;
    }
}
