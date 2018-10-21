package mode_resource;

import org.springframework.stereotype.Service;

/**
 * ファイル / ディレクトリのパーミッション変更に使用するプロパティを管理する。
 */
@Service
public class ModeProperties {
    private String path;
    private String mode;

    /**
     * @return path 操作対象とするファイル / ディレクトリのパスを返す。
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path 操作対象とするファイル / ディレクトリのパスを指定する。
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return mode ファイル / ディレクトリのパーミッション値を返す。
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode ファイル / ディレクトリに設定するパーミッション値を 3 桁の数列で指定する。
     */
    public void setMode(String mode) {
        this.mode = mode;
    }
}
