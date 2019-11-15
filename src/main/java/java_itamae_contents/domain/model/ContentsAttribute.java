package java_itamae_contents.domain.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class ContentsAttribute implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private String path;
    private String encoding;

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

    /**
     * @return encoding 文字エンコーディングを返す。
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * @param encoding 文字エンコーディングを指定する。
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
