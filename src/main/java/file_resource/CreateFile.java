package file_resource;

import java.io.File;
import java.io.IOException;

/**
 * ファイルを作成する。
 */
public class CreateFile implements FileState {
    private String path;
    private int code;
    private String message;

    /**
    * @param path 作成対象とするファイルのパスを指定する。
    */
    public CreateFile(String path) {
        this.path = path;
    }

    /**
    * 終了コードとメッセージを初期化する。
    */
    @Override
    public void initStatus() {
        this.code = 0;
        this.message = null;
    }

    /**
    * ファイルを作成する。
    */
    @Override
    public void runCommand() {
        this.initStatus();

        try {
            new File(this.path).createNewFile();
            this.code = 2;
            this.message = null;
        } catch (IOException e) {
            this.message = "エラーが発生しました。 " + e.toString();
            this.errorTerminate();
        }
    }

    /**
    * エラーメッセージを出力する。
    */
    @Override
    public void errorTerminate() {
        this.code = 1;
        System.err.println(this.message);
    }

    /**
    * @return code 終了ステータスを返す。
    */
    public int getCode() {
        return code;
    }

}
