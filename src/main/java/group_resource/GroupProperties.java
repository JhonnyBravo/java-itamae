package group_resource;

/**
 * グループ所有者の変更に使用するプロパティを管理する。
 */
public class GroupProperties {
    private String path;
    private String group;

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
     * @return group グループ所有者のグループ名を返す。
     */
    public String getGroup() {
        return group;
    }

    /**
     * @param group 新しく設定するグループ所有者のグループ名を指定する。
     */
    public void setGroup(String group) {
        this.group = group;
    }
}
