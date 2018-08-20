package directory_resource;

import java.io.File;

import group_resource.SetGroupPrincipal;
import mode_resource.SetPermission;
import owner_resource.SetUserPrincipal;
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

    /**
     * ディレクトリの所有書を変更する。
     * 
     * @param owner 新しい所有者として設定するユーザの名前を指定する。
     */
    public void setOwner(String owner) {
        SetUserPrincipal sup = new SetUserPrincipal(this.path, owner);

        if (new File(this.path).isDirectory()) {
            sup.runCommand();
        } else {
            sup.initStatus();
        }

        this.setCode(sup.getCode());
    }

    /**
     * ディレクトリのグループ所有者を変更する。
     * 
     * @param group 新しいグループ所有者として設定するグループの名前を指定する。
     */
    public void setGroup(String group) {
        SetGroupPrincipal sgp = new SetGroupPrincipal(this.path, group);

        if (new File(this.path).isDirectory()) {
            sgp.runCommand();
        } else {
            sgp.initStatus();
        }

        this.setCode(sgp.getCode());
    }

    /**
     * ディレクトリのパーミッションを変更する。
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

        if (new File(this.path).isDirectory()) {
            sp.runCommand();
        } else {
            sp.initStatus();
        }

        this.setCode(sp.getCode());
    }
}
