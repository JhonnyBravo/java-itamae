package java_itamae.domain.component.group;

import java.nio.file.attribute.GroupPrincipal;

import java_itamae.domain.component.common.BaseComponent;

/** ファイル/ディレクトリのグループ所有者を管理する。 */
public interface GroupComponent extends BaseComponent {
  /**
   * {@link GroupPrincipal} を生成する。
   *
   * @param group 新しいグループ所有者として設定するグループ名を指定する。
   * @return group {@link GroupPrincipal} を生成して返す。
   * @throws Exception {@link java.lang.Exception}
   */
  GroupPrincipal createGroup(String group) throws Exception;

  /**
   * {@link GroupPrincipal} を取得する。
   *
   * @param path 操作対象とするファイル/ディレクトリのパスを指定する。
   * @return group 現在のグループ所有者として設定されている {@link GroupPrincipal} を生成して返す。
   * @throws Exception {@link java.lang.Exception}
   */
  GroupPrincipal getGroup(String path) throws Exception;

  /**
   * ファイル/ディレクトリのグループ所有者を変更する。
   *
   * @param path 操作対象とするファイル/ディレクトリのパスを指定する。
   * @param group 新しいグループ所有者として設定するグループ名を指定する。
   * @return status
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: グループ所有者が変更されたことを表す。
   *     </ul>
   */
  int updateGroup(String path, String group);
}
