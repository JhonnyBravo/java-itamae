package owner_resource;

import java.nio.file.attribute.UserPrincipal;

/**
 * ファイルまたはディレクトリの所有者を管理する。
 */
public interface OwnerResource {
    /**
     * 新しい所有者として設定する UserPrincipal を生成する。
     * 
     * @param owner 操作対象とするユーザの名前を指定する。
     * @return UserPrincipal
     */
    public UserPrincipal createOwner(String owner);

    /**
     * ファイルまたはディレクトリの現在の所有者を取得する。
     * 
     * @param path 操作対象とするファイルまたはディレクトリのパスを指定する。
     * @return UserPrincipal
     */
    public UserPrincipal getOwner(String path);

    /**
     * ファイルまたはディレクトリの所有者を変更する。
     * 
     * @param path  操作対象とするファイルまたはディレクトリのパスを指定する。
     * @param owner 新しい所有者として設定するユーザの名前を指定する。
     */
    public void setOwner(String path, String owner);
}
