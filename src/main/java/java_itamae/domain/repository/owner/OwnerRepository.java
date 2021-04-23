package java_itamae.domain.repository.owner;

import java.nio.file.attribute.UserPrincipal;

/**
 * リソースの所有者を管理する。
 */
public interface OwnerRepository {
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
   * @param path 操作対象とするリソースのパスを指定する。
   * @return owner 現在の所有者として設定されている {@link UserPrincipal} を取得する。
   * @throws Exception {@link java.lang.Exception}
   */
  UserPrincipal getOwner(String path) throws Exception;

  /**
   * リソースの所有者を変更する。
   *
   * @param path 操作対象とするリソースのパスを指定する。
   * @param owner 新しい所有者として設定するユーザ名を指定する。
   * @return status
   *         <ul>
   *         <li>true: 所有者が変更されたことを表す。</li>
   *         <li>false: 所有者が変更されなかったことを表す。</li>
   *         </ul>
   * @throws Exception {@link java.lang.Exception}
   */
  boolean setOwner(String path, String owner) throws Exception;
}
