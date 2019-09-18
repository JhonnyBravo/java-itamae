package content_resource;

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
abstract public class ContentResource<T> {
    private final String path;
    private String encoding;

    /**
     * @param path 操作対象とするファイルのパスを指定する。
     */
    public ContentResource(String path) {
        this.path = path;
        encoding = System.getProperty("file.encoding");
    }

    /**
     * @param encoding 文字エンコーディングを指定する。(既定値: OS に標準設定されている文字エンコーディング)
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * @return 操作対象とするファイルのパスを返す。
     */
    protected String getPath() {
        return path;
    }

    /**
     * @return reader ファイルストリームを読取モードで開いて返す。
     * @throws UnsupportedEncodingException {@link java.io.UnsupportedEncodingException}
     * @throws FileNotFoundException        {@link java.io.FileNotFoundException}
     */
    protected Reader getReader() throws UnsupportedEncodingException, FileNotFoundException {
        Reader reader = null;

        if (!new File(path).isFile()) {
            throw new FileNotFoundException(path + " が見つかりません。");
        }

        try {
            reader = new InputStreamReader(new FileInputStream(path), encoding);
        } catch (final UnsupportedEncodingException e) {
            throw e;
        }

        return reader;
    }

    /**
     * @return writer ファイルストリームを書込みモードで開いて返す。
     * @throws UnsupportedEncodingException {@link java.io.UnsupportedEncodingException}
     * @throws FileNotFoundException        {@link java.io.FileNotFoundException}
     */
    protected Writer getWriter() throws UnsupportedEncodingException, FileNotFoundException {
        Writer writer = null;

        if (!new File(path).isFile()) {
            throw new FileNotFoundException(path + " が見つかりません。");
        }

        try {
            writer = new OutputStreamWriter(new FileOutputStream(path), encoding);
        } catch (final UnsupportedEncodingException e) {
            throw e;
        }

        return writer;
    }

    /**
     * ファイル書込みを実行する。
     *
     * @param model 書込み対象とするモデルオブジェクトを指定する。
     * @return status
     *         <ul>
     *         <li>true: 書込みに成功したことを表す。</li>
     *         <li>false: 書込みに失敗したことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    abstract public boolean setContent(T model) throws Exception;

    /**
     * @return model ファイルを読込み、モデルオブジェクトに変換して返す。
     * @throws Exception {@link java.lang.Exception}
     */
    abstract public T getContent() throws Exception;

    /**
     * ファイルの内容を削除する。
     *
     * @return status
     *         <ul>
     *         <li>true: 書込みに成功したことを表す。</li>
     *         <li>false: 書込みに失敗したことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    abstract public boolean deleteContent() throws Exception;
}
