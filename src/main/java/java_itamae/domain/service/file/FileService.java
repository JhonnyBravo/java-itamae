package java_itamae.domain.service.file;

import java_itamae.domain.model.file.FileResourceModel;

/** ファイルの操作を管理する。 */
public interface FileService {
  /**
   * ファイルを作成し、所有者・グループ所有者・パーミッションの設定を変更する。
   *
   * @param attr 操作対象とするファイルの設定情報を納めた Attribute オブジェクトを指定する。
   * @return status
   *     <p>以下の場合に true を返す。
   *     <ul>
   *       <li>ファイルが新規作成された場合
   *       <li>ファイルの所有者が変更された場合
   *       <li>ファイルのグループ所有者が変更された場合
   *       <li>ファイルのパーミッション設定が変更された場合
   *     </ul>
   *     <p>以下の場合に false を返す。
   *     <ul>
   *       <li>ファイルが作成されなかった場合
   *       <li>ファイルの所有者が変更されなかった場合
   *       <li>ファイルのグループ所有者が変更されなかった場合
   *       <li>ファイルのパーミッション設定が変更されなかった場合
   *     </ul>
   *
   * @throws Exception {@link java.lang.Exception}
   */
  boolean create(FileResourceModel attr) throws Exception;

  /**
   * ファイルを削除する。
   *
   * @param attr 操作対象とするファイルの設定情報を納めた Attribute オブジェクトを指定する。
   * @return status
   *     <ul>
   *       <li>true: ファイルが削除されたことを表す。
   *       <li>false: ファイルが削除されなかったことを表す。
   *     </ul>
   *
   * @throws Exception {@link java.lang.Exception}
   */
  boolean delete(FileResourceModel attr) throws Exception;
}
