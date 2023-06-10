package java_itamae.domain.component.contents;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java_itamae.domain.component.common.StreamComponent;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.status.Status;

/** テキストファイルの読み書きを管理する。 */
public interface ContentsComponent extends StreamComponent {
  /**
   * テキストファイルの内容を読込んで {@link List} へ変換して返す。
   *
   * <ol>
   *   <li>{@link String} の {@link List} を生成し、変数 contents へ設定する。
   *   <li>{@link ContentsComponent#getReader(ContentsModel)} から取得した {@link Reader} を基に {@link
   *       BufferedReader} を生成する。
   *   <li>テキストファイルの先頭から末尾まで一行ずつ読み込んで変数 contents に設定された {@link List} へ格納する。
   *   <li>返り値として変数 contents を返す。
   * </ol>
   *
   * @param model 操作対象とするテキストファイルの情報を収めた {@link ContentsModel} を指定する。
   * @return contents テキストファイルに記述されている文字列群を格納した {@link List} を返す。
   * @throws Exception テキストファイルの読込中に発生した例外を投げる。
   */
  List<String> getContents(ContentsModel model) throws Exception;

  /**
   * テキストファイルへの書込みを実行する。
   *
   * <ol>
   *   <li>変数 status へ {@link Status#INIT} を設定する。
   *   <li>{@link ContentsComponent#getWriter(ContentsModel)} から取得した {@link Writer} を基に {@link
   *       BufferedWriter} を生成する。
   *   <li>引数 contents に設定されている {@link List} から取得した文字列群を一行ずつテキストファイルへ上書きする。
   *   <li>変数 status へ {@link Status#DONE} を設定する。
   *   <li>返り値として変数 status を返す。
   * </ol>
   *
   * @param model 操作対象とするテキストファイルの情報を収めた {@link ContentsModel} を指定する。
   * @param contents 書込み対象とする文字列を収めた {@link List} を指定する。
   * @return status {@link Status} を返す。
   * @throws Exception テキストファイルの書込中に発生した例外を投げる。
   */
  Status updateContents(ContentsModel model, List<String> contents) throws Exception;

  /**
   * テキストファイルの内容を空にする。
   *
   * <ol>
   *   <li>変数 status へ {@link Status#INIT} を設定する。
   *   <li>{@link ContentsComponent#getContents(ContentsModel)} から取得した {@link List} を変数 curContents
   *       へ設定する。
   *   <li>変数 curContents へ設定された {@link List} が空であるか確認する。
   *       <ul>
   *         <li>空である場合、後続処理へ遷移する。
   *         <li>空ではない場合、 {@link ContentsComponent#getWriter(ContentsModel)} から取得した {@link Writer}
   *             を基に {@link BufferedWriter} を生成し、テキストファイルへ空文字を書き込むことでテキストファイルの内容を空にする。変数 status へ
   *             {@link Status#DONE} を設定する。
   *       </ul>
   *   <li>返り値として変数 status を返す。
   * </ol>
   *
   * @param model 操作対象とするテキストファイルの情報を収めた {@link ContentsModel} を指定する。
   * @return status {@link Status} を返す。
   * @throws Exception テキストファイルの書込中に発生した例外を投げる。
   */
  Status deleteContents(ContentsModel model) throws Exception;
}
