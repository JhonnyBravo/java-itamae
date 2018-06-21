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
     * ファイルを作成する。
     */
    @Override
    public void changed() {
        try {
            new File(this.path).createNewFile();
            this.code = 2;
            this.message = null;
        } catch (IOException e) {
            this.code = 1;
            this.message = "エラーが発生しました。 " + e.toString();
            this.error();
        }
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
