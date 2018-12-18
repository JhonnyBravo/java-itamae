package group_resource;

import java.nio.file.attribute.GroupPrincipal;

/**
 * ファイルまたはディレクトリのグループ所有者を管理する。
 */
public interface GroupResource {
    /**
     * 新しいグループ所有者として設定する GroupPrincipal を生成する。
     * 
     * @param group 新しいグループ所有者として設定するグループの名前を指定する。
     * @return GroupPrincipal
     */
    public GroupPrincipal createGroup(String group);

    /**
     * ファイルまたはディレクトリの現在のグループ所有者を取得する。
     * 
     * @param path 操作対象とするファイルまたはディレクトリのパスを指定する。
     * @return GroupPrincipal
     */
    public GroupPrincipal getGroup(String path);

    /**
     * ファイルまたはディレクトリのグループ所有者を変更する。
     * 
     * @param path  操作対象とするファイルまたはディレクトリのパスを指定する。
     * @param group 新しいグループ所有者として設定するグループの名前を指定する。
     */
    public void setGroup(String path, String group);
}
