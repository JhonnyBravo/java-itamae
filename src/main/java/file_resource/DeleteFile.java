package file_resource;

import java.io.File;

/**
 * ファイルを削除する。
 */
public class DeleteFile implements FileState {
    private String path;
    private int code;
    private String message;

    /**
    * @param path 削除対象とするファイルのパスを指定する。
    */
    public DeleteFile(String path) {
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
    * ファイルを削除する。
    */
    @Override
    public void runCommand() {
        this.initStatus();

        new File(this.path).delete();
        this.code = 2;
        this.message = null;
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
