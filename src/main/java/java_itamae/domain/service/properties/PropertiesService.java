package java_itamae.domain.service.properties;

import java.util.Map;

import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.service.common.BaseService;

/** プロパティファイルの読み書きを操作する。 */
public interface PropertiesService extends BaseService {
  /**
   * 初期化処理を実行する。
   *
   * @param model 操作対象とするファイルの情報を収めた {@link ContentsModel} を指定する。
   */
  public void init(ContentsModel model);

  /**
   * プロパティファイルからキーと値を読込み、 {@link Map} に変換して返す。
   *
   * @return 変換された {@link Map} を返す。
   * @throws Exception {@link Exception}
   */
  Map<String, String> getProperties() throws Exception;

  /**
   * プロパティファイルから値を取得する。
   *
   * @param key 取得対象とするプロパティのキー名を指定する。
   * @return property key に指定したプロパティの値を返す。
   * @throws Exception {@link Exception}
   */
  String getProperty(String key) throws Exception;

  /**
   * プロパティファイルへ新規プロパティを追記する。
   *
   * @param key 新規登録するプロパティのキー名を指定する。
   * @param value 新規登録するプロパティの値を指定する。
   * @return status 終了ステータスを返す。
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 書込みを実行して正常終了したことを表す。
   *     </ul>
   */
  int createProperty(String key, String value);

  /**
   * 既存のプロパティを上書きする。
   *
   * @param key 上書き対象とするプロパティのキー名を指定する。
   * @param value プロパティへ上書きする値を指定する。
   * @return status 終了ステータスを返す。
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 書込みを実行して正常終了したことを表す。
   *     </ul>
   */
  int updateProperty(String key, String value);

  /**
   * 既存のプロパティを削除する。
   *
   * @param key 削除対象とするプロパティのキー名を指定する。
   * @return status 終了ステータスを返す。
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 削除を実行して正常終了したことを表す。
   *     </ul>
   */
  int deleteProperty(String key);
}
