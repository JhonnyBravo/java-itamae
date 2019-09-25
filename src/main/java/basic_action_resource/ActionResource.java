package basic_action_resource;

/**
 * リソースの作成・削除と、所有者・グループ所有者・パーミッションの設定変更を実行する。
 */
public interface ActionResource {
    public abstract boolean create() throws Exception;

    public abstract boolean delete();

    public abstract boolean setOwner(String owner) throws Exception;

    public abstract boolean setGroup(String group) throws Exception;

    public abstract boolean setMode(String mode) throws Exception;
}
