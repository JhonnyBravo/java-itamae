package owner_resource;

/**
 * ファイルまたはディレクトリの所有者の変更に使用するプロパティを管理する。
 */
public class OwnerProperties {
    private String path;
    private String owner;

    /**
     * @return path 操作対象とするファイルまたはディレクトリのパスを返す。
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path 操作対象とするファイルまたはディレクトリのパスを指定する。
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return owner ファイルまたはディレクトリの所有者を返す。
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner ファイルまたはディレクトリの所有者として設定するユーザ名を指定する。
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }
}
