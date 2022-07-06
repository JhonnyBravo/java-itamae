package java_itamae.domain.service.directory;

import java_itamae.domain.model.directory.DirectoryResourceModel;

/** ディレクトリの操作を管理する。 */
public interface DirectoryService {
  /**
   * ディレクトリを再帰的に操作するかどうかを設定する。
   *
   * @param recursive
   *     <ul>
   *       <li>true: ディレクトリを再帰的に作成 / 削除するように設定する。
   *       <li>false: ディレクトリを再帰的に作成 / 削除しないように設定する。(既定値)
   *     </ul>
   */
  void setRecursive(boolean recursive);

  /**
   * ディレクトリを作成し、所有者・グループ所有者・パーミッションの設定を変更する。
   *
   * @param attr 操作対象とするディレクトリの設定情報を納めた Attribute オブジェクトを指定する。
   * @return status
   *     <p>以下の場合に true を返す。
   *     <ul>
   *       <li>ディレクトリが新規作成された場合
   *       <li>ディレクトリの所有者が変更された場合
   *       <li>ディレクトリのグループ所有者が変更された場合
   *       <li>ディレクトリのパーミッション設定が変更された場合
   *     </ul>
   *     <p>以下の場合に false を返す。
   *     <ul>
   *       <li>ディレクトリが作成されなかった場合
   *       <li>ディレクトリの所有者が変更されなかった場合
   *       <li>ディレクトリのグループ所有者が変更されなかった場合
   *       <li>ディレクトリのパーミッション設定が変更されなかった場合
   *     </ul>
   *
   * @throws Exception {@link java.lang.Exception}
   */
  boolean create(DirectoryResourceModel attr) throws Exception;

  /**
   * ディレクトリを削除する。
   *
   * @param attr 操作対象とするディレクトリの設定情報を納めた Attribute オブジェクトを指定する。
   * @return status
   *     <ul>
   *       <li>true: ディレクトリが削除されたことを表す。
   *       <li>false: ディレクトリが削除されなかったことを表す。
   *     </ul>
   *
   * @throws Exception {@link java.lang.Exception}
   */
  boolean delete(DirectoryResourceModel attr) throws Exception;
}
