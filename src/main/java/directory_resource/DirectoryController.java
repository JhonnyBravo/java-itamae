package directory_resource;

import java.io.File;

import status_resource.StatusBean;

/**
 * ディレクトリを作成または削除する。
 */
public class DirectoryController extends StatusBean {
    private String path;

    /**
     * @param path 操作対象とするディレクトリのパスを指定する。
     */
    public DirectoryController(String path) {
        this.path = path;
    }

    /**
     * <ul>
     * <li>ディレクトリが既に存在する場合: 何もしない。</li>
     * <li>ディレクトリが存在しない場合: ディレクトリを新規作成する。</li>
     * </ul>
     */
    public void create() {
        CreateDirectory cd = new CreateDirectory(this.path);

        if (new File(this.path).isDirectory()) {
            cd.initStatus();
        } else {
            System.out.println(this.path + " を作成します。");
            cd.runCommand();
        }

        this.setCode(cd.getCode());
    }

    /**
     * <ul>
     * <li>ディレクトリが存在する場合: ディレクトリを削除する。</li>
     * <li>ディレクトリが存在しない場合: 何もしない。</li>
     * </ul>
     */
    public void delete() {
        DeleteDirectory dd = new DeleteDirectory(this.path);

        if (new File(this.path).isDirectory()) {
            System.out.println(this.path + " を削除します。");
            dd.runCommand();
        } else {
            dd.initStatus();
        }

        this.setCode(dd.getCode());
    }
}
