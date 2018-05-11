package file_resource;

/**
 * メソッド実行前後のステータスを管理する。
 */
public class StatusBean {
    private String resourceName = null;
    private int code = 0;
    private String message = null;
    private String status = null;

    /**
    * @return resourceName ステータスの管理対象とするリソースの名前を返す。
    */
    public String getResourceName() {
        return resourceName;
    }

    /**
    * @param resourceName ステータスの管理対象として設定するリソースの名前を指定する。
    */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
    * @return code メソッド実行前後の状態を表すコード値を返す。
    */
    public int getCode() {
        return code;
    }

    /**
    * <ul>
    * <li>0: 成功も失敗もなく終了した状態を表す。</li>
    * <li>1: 異常終了した状態を表す。</li>
    * <li>2: リソースの操作に成功して終了した状態を表す。</li>
    * </ul>
    *
    * @param code メソッド実行前後の状態を表すコード値として設定する数値を指定する。
    */
    public void setCode(int code) {
        this.code = code;
    }

    /**
    * @return message メソッド実行前後にコンソールへ表示するメッセージを返す。
    */
    public String getMessage() {
        return message;
    }

    /**
    * @param message メソッド実行前後にコンソールへ表示するメッセージを指定する。
    */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
    * @return status resourceName と message を連結したステータス文字列を返す。
    */
    public String getStatus() {
        if (this.resourceName != null && this.message != null) {
            //resourceName, message 両方が指定されている場合
            this.status = this.resourceName + " " + this.message;
        } else if (this.resourceName == null && this.message != null) {
            //message のみが指定されている場合
            this.status = this.message;
        } else if (this.resourceName != null && this.message == null) {
            //resourceName のみが指定されている場合
            System.err.println("message を指定してください。");
        }

        return status;
    }
}
