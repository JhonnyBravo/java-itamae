package group_resource;

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
    Properties p = new Properties();

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
     * @param path プロパティ取得対象とするファイルのパスを指定する。
     * @return groupName テスト時に使用するグループ名を返す。
     */
    public String getGroupName(String path) {
        this.initStatus();

        String groupName = null;
        InputStream input = getInputStream(path);

        if (this.getCode() == 1) {
            return groupName;
        }

        try {
            p.load(input);
            groupName = p.getProperty("groupName");
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。 " + e.toString());
            return groupName;
        }

        if (groupName == null) {
            this.errorTerminate("groupName プロパティが見つかりません。");
            return groupName;
        }

        this.setCode(2);
        return groupName;
    }
}
