package java_itamae.domain.model;

import java.io.Serializable;

/**
 * リソースの管理に使用する属性を管理する。
 */
public class Attribute implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String path;
    private String owner;
    private String group;
    private String mode;

    /**
     * @return path 操作対象とするリソースのパスを返す。
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path 操作対象とするリソースのパスを指定する。
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return owner リソースの所有者を返す。
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner リソースに設定する所有者を指定する。
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return group リソースのグループ所有者を返す。
     */
    public String getGroup() {
        return group;
    }

    /**
     * @param group リソースに設定するグループ所有者を指定する。
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * @return mode リソースのパーミッション値を返す。
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode リソースに設定するパーミッション値を 3 桁の整数で指定する。
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
        this.mode = mode;
    }
}
