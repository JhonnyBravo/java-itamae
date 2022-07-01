package java_itamae.domain.service.properties;

import java.util.Map;

/** プロパティファイルの読み書きを操作する。 */
public interface PropertiesService {
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
   * @return status
   *     <ul>
   *       <li>true: 新規登録に成功したことを表す。
   *       <li>false: 新規登録を実行しなかったことを表す。
   *     </ul>
   *
   * @throws Exception {@link Exception}
   */
  boolean createProperty(String key, String value) throws Exception;

  /**
   * 既存のプロパティを上書きする。
   *
   * @param key 上書き対象とするプロパティのキー名を指定する。
   * @param value プロパティへ上書きする値を指定する。
   * @return status
   *     <ul>
   *       <li>true: プロパティの上書きに成功したことを表す。
   *       <li>false: プロパティを上書きしなかったことを表す。
   *     </ul>
   *
   * @throws Exception {@link Exception}
   */
  boolean updateProperty(String key, String value) throws Exception;

  /**
   * 既存のプロパティを削除する。
   *
   * @param key 削除対象とするプロパティのキー名を指定する。
   * @return status
   *     <ul>
   *       <li>true: 削除に成功したことを表す。
   *       <li>false: 削除を実行しなかったことを表す。
   *     </ul>
   *
   * @throws Exception {@link Exception}
   */
  boolean deleteProperty(String key) throws Exception;
}
