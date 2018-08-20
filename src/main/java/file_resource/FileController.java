package file_resource;

import java.io.File;

import group_resource.SetGroupPrincipal;
import mode_resource.SetPermission;
import owner_resource.SetUserPrincipal;
import status_resource.StatusBean;

/**
 * ファイルの有無を確認してコマンドを実行する。
 */
public class FileController extends StatusBean {
    private String path;

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
            cf.initStatus();
        } else {
            System.out.println(this.path + " を作成します。");
            cf.runCommand();
        }

        this.setCode(cf.getCode());
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
            df.runCommand();
        } else {
            df.initStatus();
        }

        this.setCode(df.getCode());
    }

    /**
     * ファイル所有者を変更する。
     * 
     * @param owner 新しい所有者として設定するユーザの名前を指定する。
     */
    public void setOwner(String owner) {
        SetUserPrincipal sup = new SetUserPrincipal(this.path, owner);

        if (new File(this.path).isFile()) {
            sup.runCommand();
        } else {
            sup.initStatus();
        }

        this.setCode(sup.getCode());
    }

    /**
     * ファイルのグループ所有者を変更する。
     * 
     * @param group 新しいグループ所有者として設定するグループの名前を指定する。
     */
    public void setGroup(String group) {
        SetGroupPrincipal sgp = new SetGroupPrincipal(this.path, group);

        if (new File(this.path).isFile()) {
            sgp.runCommand();
        } else {
            sgp.initStatus();
        }

        this.setCode(sgp.getCode());
    }

    /**
     * ファイルパーミッションを変更する。
     * 
     * @param mode 新しく設定するパーミッションを 3 桁の数列で指定する。
     *             <ul>
     *             <li>0: ---</li>
     *             <li>1: --x</li>
     *             <li>2: -w-</li>
     *             <li>3: -wx</li>
     *             <li>4: r--</li>
     *             <li>5: r-x</li>
     *             <li>6: rw-</li>
     *             <li>7: rwx</li>
     *             </ul>
     */
    public void setMode(String mode) {
        SetPermission sp = new SetPermission(this.path, mode);

        if (new File(this.path).isFile()) {
            sp.runCommand();
        } else {
            sp.initStatus();
        }

        this.setCode(sp.getCode());
    }

}
