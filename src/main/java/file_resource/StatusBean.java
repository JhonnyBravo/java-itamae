package file_resource;

/**
 * メソッド実行直後の終了コードとメッセージを管理する。
 */
public class StatusBean {
    private int code;
    private String message;

    /**
     * <ul>
     * <li>0: エラーも無く、リソースの変更も無く終了した状態を表す。</li>
     * <li>1: エラー終了した状態を表す。</li>
     * <li>2: リソースを変更して正常終了した状態を表す。</li>
     * </ul>
     * 
     * @return code メソッド実行直後の終了コードを返す。
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code メソッド実行直後の終了コードを設定する。
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return message メソッド実行直後の終了メッセージを返す。
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message メソッド実行直後の終了メッセージを設定する。
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
