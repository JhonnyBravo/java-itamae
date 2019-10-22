package java_itamae_contents.domain.repository.stream;

import java.io.Reader;
import java.io.Writer;

import java_itamae_contents.domain.model.ContentsAttribute;

/**
 * テキストファイルの読み書きに使用するストリームを取得する。
 */
public interface StreamRepository {
    /**
     * @param attr 操作対象とする ContentsAttribute を指定する。
     * @return reader ファイルストリームを読取モードで開いて返す。
     * @throws Exception {@link java.lang.Exception}
     */
    public Reader getReader(ContentsAttribute attr) throws Exception;

    /**
     * @param attr 操作対象とする ContentsAttribute を指定する。
     * @return writer ファイルストリームを書込モードで開いて返す。
     * @throws Exception {@link java.lang.Exception}
     */
    public Writer getWriter(ContentsAttribute attr) throws Exception;
}
