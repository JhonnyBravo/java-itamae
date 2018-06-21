package file_resource;

import java.io.File;

/**
 * ファイルの有無を確認してコマンドを実行する。
 */
public class FileController {
    private String path;
    private int code;

    /**
     * @param path 操作対象とするファイルのパスを指定する。
     */
    public FileController(String path) {
        this.path = path;
    }

    /**
     * <ul>
     * <li>ファイルが既に存在する場合: 何もしない。</li>
     * <li>ファイルが存在しない場合: ファイルを新規作成する。</li>
     * </ul>
     */
    public void create() {
        CreateFile cf = new CreateFile(this.path);

        if (new File(this.path).isFile()) {
            cf.noChanged();
        } else {
            System.out.println(this.path + " を作成します。");
            cf.changed();
        }

        this.code = cf.getCode();
    }

    /**
     * <ul>
     * <li>ファイルが存在する場合: ファイルを削除する。</li>
     * <li>ファイルが存在しない場合: 何もしない。</li>
     * </ul>
     */
    public void delete() {
        DeleteFile df = new DeleteFile(this.path);

        if (new File(this.path).isFile()) {
            System.out.println(this.path + " を削除します。");
            df.changed();
        } else {
            df.noChanged();
        }

        this.code=df.getCode();
    }

    /**
     * @return code 終了コードを返す。
     */
    public int getCode() {
        return code;
    }
}
