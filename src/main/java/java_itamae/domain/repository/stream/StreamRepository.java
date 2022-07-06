package java_itamae.domain.repository.stream;

import java.io.Reader;
import java.io.Writer;
import java_itamae.domain.model.contents.ContentsModel;

/** テキストファイルの読み書きに使用するストリームを取得する。 */
public interface StreamRepository {
  /**
   * ファイルストリームを読取モードで開いて返す。
   *
   * @param attr 操作対象とする {@link ContentsModel} を指定する。
   * @return reader {@link Reader} を返す。
   * @throws Exception {@link java.lang.Exception}
   */
  Reader getReader(ContentsModel attr) throws Exception;

  /**
   * ファイルストリームを書込モードで開いて返す。
   *
   * @param attr 操作対象とする {@link ContentsModel} を指定する。
   * @return writer {@link Writer} を返す。
   * @throws Exception {@link java.lang.Exception}
   */
  Writer getWriter(ContentsModel attr) throws Exception;
}
