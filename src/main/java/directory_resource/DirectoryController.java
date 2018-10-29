package directory_resource;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import group_resource.SetGroupPrincipal;
import mode_resource.SetPermission;
import owner_resource.SetUserPrincipal;
import status_resource.StatusController;

/**
 * ディレクトリを作成または削除する。
 */
@Service
@Import({ SetUserPrincipal.class, SetGroupPrincipal.class, SetPermission.class })
public class DirectoryController extends StatusController {
    @Autowired
    private DirectoryProperties dp;

    @Autowired
    private CreateDirectory cd;

    @Autowired
    private DeleteDirectory dd;

    @Autowired
    private SetUserPrincipal sup;

    @Autowired
    private SetGroupPrincipal sgp;

    @Autowired
    private SetPermission sp;

    /**
     * @param path 操作対象とするディレクトリのパスを指定する。
     */
    public void init(String path) {
        dp.setPath(path);
    }

    /**
     * <ul>
     * <li>ディレクトリが既に存在する場合: 何もしない。</li>
     * <li>ディレクトリが存在しない場合: ディレクトリを新規作成する。</li>
     * </ul>
     */
    public void create() {
        cd.init(dp.getPath());
        cd.runCommand();
        this.setCode(cd.getCode());
    }

    /**
     * <ul>
     * <li>ディレクトリが存在する場合: ディレクトリを削除する。</li>
     * <li>ディレクトリが存在しない場合: 何もしない。</li>
     * </ul>
     */
    public void delete() {
        dd.init(dp.getPath());
        dd.runCommand();
        this.setCode(dd.getCode());
    }

    /**
     * ディレクトリの所有書を変更する。
     * 
     * @param owner 新しい所有者として設定するユーザの名前を指定する。
     */
    public void setOwner(String owner) {
        File f = new File(dp.getPath());

        if (!f.isDirectory()) {
            this.setMessage(dp.getPath() + " が見つかりません。");
            this.errorTerminate();
            return;
        }

        sup.init(dp.getPath(), owner);
        sup.runCommand();
        this.setCode(sup.getCode());
    }

    /**
     * ディレクトリのグループ所有者を変更する。
     * 
     * @param group 新しいグループ所有者として設定するグループの名前を指定する。
     */
    public void setGroup(String group) {
        File f = new File(dp.getPath());

        if (!f.isDirectory()) {
            this.setMessage(dp.getPath() + " が見つかりません。");
            this.errorTerminate();
            return;
        }

        sgp.init(dp.getPath(), group);
        sgp.runCommand();
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
        File f = new File(dp.getPath());

        if (!f.isDirectory()) {
            this.setMessage(dp.getPath() + " が見つかりません。");
            this.errorTerminate();
            return;
        }

        sp.init(dp.getPath(), mode);
        sp.runCommand();
        this.setCode(sp.getCode());
    }
}
