package content_resource.domain.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * テキストファイルの読み書きに使用するストリームを管理する。
 */
public class IOStreamResource {
    private String encoding;
    private final String path;
    private final File file;

    /**
     * @param path 操作対象とするファイルのパスを指定する。
     */
    public IOStreamResource(String path) {
        this.path = path;
        file = new File(path);
        encoding = System.getProperty("file.encoding");
    }

    /**
     * @param encoding 文字エンコーディングを指定する。(既定値: OS に標準設定されている文字エンコーディング)
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * @return reader ファイルストリームを読取モードで開いて返す。
     * @throws UnsupportedEncodingException {@link java.io.UnsupportedEncodingException}
     * @throws FileNotFoundException        {@link java.io.FileNotFoundException}
     */
    public Reader getReader() throws FileNotFoundException, UnsupportedEncodingException {
        Reader reader = null;

        if (!file.isFile()) {
            throw new FileNotFoundException(path + " が見つかりません。");
        }

        reader = new InputStreamReader(new FileInputStream(path), encoding);
        return reader;
    }

    /**
     * @return writer ファイルストリームを書込みモードで開いて返す。
     * @throws UnsupportedEncodingException {@link java.io.UnsupportedEncodingException}
     * @throws FileNotFoundException        {@link java.io.FileNotFoundException}
     */
    public Writer getWriter() throws FileNotFoundException, UnsupportedEncodingException {
        Writer writer = null;

        if (!file.isFile()) {
            throw new FileNotFoundException(path + " が見つかりません。");
        }

        writer = new OutputStreamWriter(new FileOutputStream(path), encoding);
        return writer;
    }
}
