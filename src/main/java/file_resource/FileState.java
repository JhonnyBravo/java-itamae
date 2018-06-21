package file_resource;

public interface FileState {
    /**
    * 変更も異常もない場合に実行するコマンドを実装する。
    */
    public void noChanged();

    /**
    * リソースの変更が発生する場合に実行するコマンドを実装する。
    */
    public void changed();

    /**
    * エラーが発生した場合に実行するコマンドを実装する。
    */
    public void error();
}
