package java_itamae_contents.domain.repository.stream;

import java.io.Reader;
import java.io.Writer;
import java_itamae_contents.domain.model.ContentsAttribute;

/**
 * テキストファイルの読み書きに使用するストリームを取得する。
 */
public interface StreamRepository {
  /**
   * ファイルストリームを読取モードで開いて返す。
   *
   * @param attr 操作対象とする {@link ContentsAttribute} を指定する。
   * @return reader {@link Reader} を返す。
   * @throws Exception {@link java.lang.Exception}
   */
  Reader getReader(ContentsAttribute attr) throws Exception;

  /**
   * ファイルストリームを書込モードで開いて返す。
   *
   * @param attr 操作対象とする {@link ContentsAttribute} を指定する。
   * @return writer {@link Writer} を返す。
   * @throws Exception {@link java.lang.Exception}
   */
  Writer getWriter(ContentsAttribute attr) throws Exception;
}
