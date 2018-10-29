package directory_resource;

import org.springframework.stereotype.Service;

/**
 * ディレクトリの作成 / 削除に使用するプロパティを管理する。
 */
@Service
public class DirectoryProperties {
    private String path;

    /**
     * @return path 操作対象とするディレクトリのパスを返す。
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path 操作対象とするディレクトリのパスを指定する。
     */
    public void setPath(String path) {
        this.path = path;
    }
}
