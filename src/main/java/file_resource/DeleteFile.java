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
        this.code = 0;
        this.message = null;
    }

    /**
     * 終了コード: 0 を設定して終了する。
     */
    @Override
    public void noChanged() {
        this.code = 0;
        this.message = null;
    }

    /**
     * ファイルを削除する。
     */
    @Override
    public void changed() {
        new File(this.path).delete();
        this.code = 2;
        this.message = null;
    }

    /**
     * エラーメッセージを出力する。
     */
    @Override
    public void error() {
        System.err.println(this.message);
    }

    /**
    * @return code 終了ステータスを返す。
    */
    public int getCode() {
        return code;
    }

}
