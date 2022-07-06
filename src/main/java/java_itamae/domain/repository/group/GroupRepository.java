package java_itamae.domain.repository.group;

import java.nio.file.attribute.GroupPrincipal;

/** リソースのグループ所有者を管理する。 */
public interface GroupRepository {
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
   * @param path 操作対象とするリソースのパスを指定する。
   * @return group 現在のグループ所有者として設定されている {@link GroupPrincipal} を生成して返す。
   * @throws Exception {@link java.lang.Exception}
   */
  GroupPrincipal getGroup(String path) throws Exception;

  /**
   * リソースのグループ所有者を変更する。
   *
   * @param path 操作対象とするリソースのパスを指定する。
   * @param group 新しいグループ所有者として設定するグループ名を指定する。
   * @return status
   *     <ul>
   *       <li>true: グループ所有者が変更されたことを表す。
   *       <li>false: グループ所有者が変更されなかったことを表す。
   *     </ul>
   *
   * @throws Exception {@link java.lang.Exception}
   */
  boolean updateGroup(String path, String group) throws Exception;
}
