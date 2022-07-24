package java_itamae.domain.component.properties;

import java.util.Map;
import java_itamae.domain.component.common.StreamComponent;
import java_itamae.domain.model.contents.ContentsModel;

/** プロパティファイルの読み書きを操作する。 */
public interface PropertiesComponent extends StreamComponent {
  /**
   * プロパティファイルを読込んで {@link Map} に変換して返す。
   *
   * @param model 操作対象とするファイルの情報を収めた {@link ContentsModel} を指定する。
   * @return properties 変換された {@link Map} を返す。
   * @throws Exception {@link Exception}
   */
  Map<String, String> getProperties(ContentsModel model) throws Exception;

  /**
   * プロパティファイルへの書込みを実行する。
   *
   * @param model 操作対象とするファイルの情報を収めた {@link ContentsModel} を指定する。
   * @param map 書込み対象とする {@link Map} を指定する。
   * @param comment プロパティファイルへ書込むコメントを指定する。
   * @return status 終了ステータスを返す。
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 書込みを実行して正常終了したことを表す。
   *     </ul>
   */
  int updateProperties(ContentsModel model, Map<String, String> map, String comment);

  /**
   * プロパティファイルから全てのプロパティを削除する。
   *
   * @param model 操作対象とするファイルの情報を収めた {@link ContentsModel} を指定する。
   * @param comment プロパティファイルへ書込むコメントを指定する。
   * @return status 終了ステータスを返す。
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 削除を実行して正常終了したことを表す。
   *     </ul>
   */
  int deleteProperties(ContentsModel model, String comment);
}
