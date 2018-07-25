package file_resource;

/**
 * 終了コードとメッセージの初期化と、エラー終了時のメッセージ出力を実行する。
 */
public class StatusController extends StatusBean {
    /**
     * 終了コードとメッセージの初期化を実行する。
     */
    public void initStatus() {
        this.setCode(0);
        this.setMessage(null);
    }

    /**
     * エラー終了時にメッセージを出力する。
     */
    public void errorTerminate() {
        this.setCode(1);
        System.err.println(this.getMessage());
    }
}
