package basic_action_resource;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * リソースの作成・削除と、所有者・グループ所有者・パーミッションの設定変更を実行する。
 */
public abstract class ActionResource {
    public abstract boolean create() throws IOException;

    public abstract boolean delete();

    public abstract boolean setOwner(String owner) throws FileNotFoundException, IOException;

    public abstract boolean setGroup(String group) throws FileNotFoundException, IOException;

    public abstract boolean setMode(String mode) throws FileNotFoundException, IOException;
}
