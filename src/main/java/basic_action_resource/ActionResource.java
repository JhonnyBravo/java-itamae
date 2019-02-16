package basic_action_resource;

import status_resource.Status;

/**
 * リソースを作成または削除する。
 */
public abstract class ActionResource extends Status {
    public abstract void create();

    public abstract void delete();
}
