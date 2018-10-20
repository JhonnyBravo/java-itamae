package file_resource;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import group_resource.SetGroupPrincipal;
import mode_resource.SetPermission;
import owner_resource.SetUserPrincipal;
import status_resource.StatusController;

/**
 * ファイルの作成 / 削除とファイル所有者の変更 / グループ所有者の変更 / ファイルパーミッションの変更を実行する。
 */
@Service
@Import({ SetGroupPrincipal.class, SetPermission.class, SetUserPrincipal.class })
public class FileController extends StatusController {
    @Autowired
    private FileProperties fp;

    @Autowired
    private CreateFile cf;

    @Autowired
    private DeleteFile df;

    @Autowired
    private SetUserPrincipal sup;

    @Autowired
    private SetGroupPrincipal sgp;

    @Autowired
    private SetPermission sp;

    /**
     * @param path 操作対象とするファイルのパスを指定する。
     */
    public void init(String path) {
        fp.setPath(path);
    }

    /**
     * <ul>
     * <li>ファイルが既に存在する場合: 何もしない。</li>
     * <li>ファイルが存在しない場合: ファイルを新規作成する。</li>
     * </ul>
     */
    public void create() {
        cf.init(fp.getPath());
        cf.runCommand();
        this.setCode(cf.getCode());
    }

    /**
     * <ul>
     * <li>ファイルが存在する場合: ファイルを削除する。</li>
     * <li>ファイルが存在しない場合: 何もしない。</li>
     * </ul>
     */
    public void delete() {
        df.init(fp.getPath());
        df.runCommand();
        this.setCode(df.getCode());
    }

    /**
     * ファイル所有者を変更する。
     * 
     * @param owner 新しい所有者として設定するユーザの名前を指定する。
     */
    public void setOwner(String owner) {
        File f = new File(fp.getPath());

        if (!f.isFile()) {
            this.setMessage(fp.getPath() + " が見つかりません。");
            this.errorTerminate();
            return;
        }

        sup.init(fp.getPath(), owner);
        sup.runCommand();
        this.setCode(sup.getCode());
    }

    /**
     * ファイルのグループ所有者を変更する。
     * 
     * @param group 新しいグループ所有者として設定するグループの名前を指定する。
     */
    public void setGroup(String group) {
        File f = new File(fp.getPath());

        if (!f.isFile()) {
            this.setMessage(fp.getPath() + " が見つかりません。");
            this.errorTerminate();
            return;
        }

        sgp.init(fp.getPath(), group);
        sgp.runCommand();
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
        File f = new File(fp.getPath());

        if (!f.isFile()) {
            this.setMessage(fp.getPath() + " が見つかりません。");
            this.errorTerminate();
            return;
        }

        sp.init(fp.getPath(), mode);
        sp.runCommand();
        this.setCode(sp.getCode());
    }
}
