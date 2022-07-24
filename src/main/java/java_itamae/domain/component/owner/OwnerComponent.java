package java_itamae.domain.component.owner;

import java.nio.file.attribute.UserPrincipal;
import java_itamae.domain.component.common.BaseComponent;

/** ファイル/ディレクトリの所有者を管理する。 */
public interface OwnerComponent extends BaseComponent {
  /**
   * {@link UserPrincipal} を生成する。
   *
   * @param owner 新しい所有者として設定するユーザ名を指定する。
   * @return owner {@link UserPrincipal} を生成して返す。
   * @throws Exception {@link java.lang.Exception}
   */
  UserPrincipal createOwner(String owner) throws Exception;

  /**
   * {@link UserPrincipal} を取得する。
   *
   * @param path 操作対象とするファイル/ディレクトリのパスを指定する。
   * @return owner 現在の所有者として設定されている {@link UserPrincipal} を取得する。
   * @throws Exception {@link java.lang.Exception}
   */
  UserPrincipal getOwner(String path) throws Exception;

  /**
   * ファイル/ディレクトリの所有者を変更する。
   *
   * @param path 操作対象とするファイル/ディレクトリのパスを指定する。
   * @param owner 新しい所有者として設定するユーザ名を指定する。
   * @return status
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 所有者が変更されて正常終了したことを表す。
   *     </ul>
   */
  int updateOwner(String path, String owner);
}
