package file_resource;

import org.springframework.stereotype.Service;

/**
 * ファイルの作成 / 削除に使用するプロパティを管理する。
 */
@Service
public class FileProperties {
    private String path;

    /**
     * @return path 操作対象とするファイルのパスを返す。
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path 操作対象とするファイルのパスを指定する。
     */
    public void setPath(String path) {
        this.path = path;
    }
}
