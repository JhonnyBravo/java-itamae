package java_itamae.domain.component.properties;

import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;
import java_itamae.domain.component.common.StreamComponent;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.status.Status;

/** プロパティファイルの読み書きを操作する。 */
public interface PropertiesComponent extends StreamComponent {
  /**
   * プロパティファイルを読込んで {@link Map} に変換して返す。
   *
   * <ol>
   *   <li>{@link Properties} を生成し、変数 properties へ設定する。
   *   <li>{@link PropertiesComponent#getReader(ContentsModel)} から {@link Reader} を取得し、 {@link
   *       Properties#load(Reader)} を実行してプロパティファイルを読み込む。
   *   <li>{@link Map} を生成し、変数 map へ設定する。
   *   <li>変数 properties に格納されているキーと値を取得し、変数 map へキーと値を設定する。
   *   <li>返り値として変数 map を返す。
   * </ol>
   *
   * @param model 操作対象とするファイルの情報を収めた {@link ContentsModel} を指定する。
   * @return properties 変換された {@link Map} を返す。
   * @throws Exception プロパティファイルの読込み中に発生した例外を投げる。
   */
  Map<String, String> getProperties(ContentsModel model) throws Exception;

  /**
   * プロパティファイルへの書込みを実行する。
   *
   * <ol>
   *   <li>変数 status へ {@link Status#INIT} を設定する。
   *   <li>{@link Properties} を生成し、変数 properties へ設定する。
   *   <li>引数 map に格納されているキーと値を取得し、変数 properties へキーと値を設定する。
   *   <li>{@link PropertiesComponent#getWriter(ContentsModel)} から {@link Writer} を取得し、 {@link
   *       Properties#store(Writer, String)} を実行してプロパティファイルを上書きする。
   *   <li>変数 status へ {@link Status#DONE} を設定する。
   *   <li>返り値として変数 status を返す。
   * </ol>
   *
   * @param model 操作対象とするファイルの情報を収めた {@link ContentsModel} を指定する。
   * @param map 書込み対象とするプロパティ群を収めた {@link Map} を指定する。
   * @param comment プロパティファイルへ書込むコメントを指定する。
   * @return status {@link Status} を返す。
   * @throws Exception プロパティファイルへの書込み中に発生した例外を投げる。
   */
  Status updateProperties(ContentsModel model, Map<String, String> map, String comment)
      throws Exception;

  /**
   * プロパティファイルから全てのプロパティを削除する。
   *
   * <ol>
   *   <li>変数 status へ {@link Status#INIT} を設定する。
   *   <li>{@link PropertiesComponent#getProperties(ContentsModel)} から取得したプロパティのキーと値を、変数
   *       curProperties のキーと値として設定する。
   *   <li>変数 curProperties が空の {@link Map} であるか確認する。
   *       <ul>
   *         <li>空の {@link Map} である場合は後続処理へ遷移する。
   *         <li>空の {@link Map} ではない場合は空の {@link Properties} を生成し、 {@link
   *             PropertiesComponent#getWriter(ContentsModel)} から {@link Writer} を取得して {@link
   *             Properties#store(Writer, String)} を実行する。変数 status へ {@link Status#DONE} を設定する。
   *       </ul>
   *   <li>返り値として変数 status を返す。
   * </ol>
   *
   * @param model 操作対象とするファイルの情報を収めた {@link ContentsModel} を指定する。
   * @param comment プロパティファイルへ書込むコメントを指定する。
   * @return status {@link Status} を返す。
   * @throws Exception プロパティファイルへの書込み中に発生した例外を投げる。
   */
  Status deleteProperties(ContentsModel model, String comment) throws Exception;
}
