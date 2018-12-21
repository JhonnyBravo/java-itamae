package file_resource;

/**
 * ファイルの作成・削除と所有者・グループ所有者・パーミッションの変更を実行する。
 */
public interface FileResource {
    /**
     * @param path 操作対象とするファイルのパスを指定する。
     */
    public void setPath(String path);

    /**
     * ファイルを新規作成する。
     */
    public void createFile();

    /**
     * ファイルを削除する。
     */
    public void deleteFile();

    /**
     * ファイルの所有者を変更する。
     * 
     * @param owner 新しい所有者として設定するユーザの名前を指定する。
     */
    public void setOwner(String owner);

    /**
     * ファイルのグループ所有者を変更する。
     * 
     * @param group 新しいグループ所有者として設定するユーザの名前を指定する。
     */
    public void setGroup(String group);

    /**
     * ファイルのパーミッション設定を変更する。
     * 
     * @param mode 新しく設定するパーミッション値を 3 桁の数列で指定する。
     */
    public void setMode(String mode);
}
