package java_itamae.domain.service.properties;

import java.util.Map;
import java_itamae.domain.component.properties.PropertiesComponent;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.status.Status;
import java_itamae.domain.service.common.BaseService;

/** プロパティファイルの読み書きを操作する。 */
public interface PropertiesService extends BaseService {
  /**
   * 変数 model へ {@link ContentsModel} を設定する。
   *
   * @param model 操作対象とするファイルの情報を収めた {@link ContentsModel} を指定する。
   */
  void init(ContentsModel model);

  /**
   * プロパティファイルからキーと値を読込み、 {@link Map} に変換して返す。
   *
   * <ol>
   *   <li>{@link PropertiesComponent#getProperties(ContentsModel)} を実行し、結果を返り値として返す。
   * </ol>
   *
   * @return 変換された {@link Map} を返す。
   * @throws Exception プロパティファイルの読込み中に発生した例外を投げる。
   */
  Map<String, String> getProperties() throws Exception;

  /**
   * プロパティファイルから値を取得する。
   *
   * <ol>
   *   <li>{@link PropertiesComponent#getProperties(ContentsModel)} を実行し、返り値を変数 properties へ設定する。
   *   <li>引数 key に指定されたキーが変数 properties に格納された {@link Map} に存在するか確認する。
   *       <ul>
   *         <li>キーが存在する場合、変数 properties に格納された {@link Map} からキーの値を取得し、返り値として返す。
   *         <li>キーが存在しない場合、 {@link Exception} を発生させて異常終了する。
   *       </ul>
   * </ol>
   *
   * @param key 取得対象とするプロパティのキー名を指定する。
   * @return property key に指定したプロパティの値を返す。
   * @throws Exception プロパティの取得中に発生した例外を投げる。
   */
  String getProperty(String key) throws Exception;

  /**
   * プロパティファイルへ新規プロパティを追記する。
   *
   * <ol>
   *   <li>変数 status へ {@link Status#INIT} を設定する。
   *   <li>{@link PropertiesComponent#getProperties(ContentsModel)} を実行し、返り値を変数 properties へ設定する。
   *   <li>引数 key に指定されたキーが変数 properties に格納された {@link Map} に存在するか確認する。
   *       <ul>
   *         <li>キーが存在する場合、 {@link Exception} を発生させて異常終了する。
   *         <li>キーが存在しない場合、後続処理へ遷移する。
   *       </ul>
   *   <li>変数 properties へ引数 key と引数 value に指定した値を新規登録する。
   *   <li>{@link PropertiesComponent#updateProperties(ContentsModel, Map, String)} を実行し、返り値を変数
   *       status へ設定する。
   *   <li>変数 status を返り値として返す。
   * </ol>
   *
   * @param key 新規登録するプロパティのキー名を指定する。
   * @param value 新規登録するプロパティの値を指定する。
   * @return status {@link Status} を返す。
   * @throws Exception プロパティの追記中に発生した例外を投げる。
   */
  Status createProperty(String key, String value) throws Exception;

  /**
   * 既存のプロパティを上書きする。
   *
   * <ol>
   *   <li>変数 status へ {@link Status#INIT} を設定する。
   *   <li>{@link PropertiesComponent#getProperties(ContentsModel)} を実行し、返り値を変数 properties へ設定する。
   *   <li>変数 properties へ格納された {@link Map} へ引数 key に指定されたキーが存在するか確認する。
   *       <ul>
   *         <li>キーが存在する場合、後続処理へ遷移する。
   *         <li>キーが存在しない場合、 {@link Exception} を発生させて異常終了する。
   *       </ul>
   *   <li>変数 properties から引数 key の値に対応するプロパティの値を取得し、引数 value の値と一致するか確認する。
   *       <ul>
   *         <li>一致する場合、後続処理へ遷移する。
   *         <li>一致しない場合、変数 properties の引数 key の値に対応するプロパティの値を、引数 value に指定した値へ更新する。 {@link
   *             PropertiesComponent#updateProperties(ContentsModel, Map, String)} を実行し、返り値を変数
   *             status へ設定する。
   *       </ul>
   *   <li>変数 status を返り値として返す。
   * </ol>
   *
   * @param key 上書き対象とするプロパティのキー名を指定する。
   * @param value プロパティへ上書きする値を指定する。
   * @return status {@link Status} を返す。
   * @throws Exception プロパティの更新中に発生した例外を投げる。
   */
  Status updateProperty(String key, String value) throws Exception;

  /**
   * 既存のプロパティを削除する。
   *
   * <ol>
   *   <li>変数 status へ {@link Status#INIT} を設定する。
   *   <li>{@link PropertiesComponent#getProperties(ContentsModel)} を実行し、返り値を変数 properties へ設定する。
   *   <li>変数 properties へ格納された {@link Map} に引数 key に指定されたキーが存在するか確認する。
   *       <ul>
   *         <li>存在する場合、後続処理へ遷移する。
   *         <li>存在しない場合、 {@link Exception} を発生させて異常終了する。
   *       </ul>
   *   <li>引数 properties に格納された {@link Map} から引数 key に指定されたキーを削除する。
   *   <li>{@link PropertiesComponent#updateProperties(ContentsModel, Map, String)} を実行し、返り値を変数
   *       status へ設定する。
   *   <li>変数 status を返り値として返す。
   * </ol>
   *
   * @param key 削除対象とするプロパティのキー名を指定する。
   * @return status {@link Status} を返す。
   * @throws Exception プロパティの削除中に発生した例外を投げる。
   */
  Status deleteProperty(String key) throws Exception;
}
