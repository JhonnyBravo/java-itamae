package java_itamae.domain.repository.owner;

import java.nio.file.attribute.UserPrincipal;

/**
 * リソースの所有者を管理する。
 */
public interface OwnerRepository {
    /**
     * @param owner 新し所有者として設定するユーザ名を指定する。
     * @return owner UserPrincipal を生成して返す。
     * @throws Exception {@link java.lang.Exception}
     */
    public UserPrincipal createOwner(String owner) throws Exception;

    /**
     * @param path 操作対象とするリソースのパスを指定する。
     * @return owner 現在の所有者として設定されている UserPrincipal を取得する。
     * @throws Exception {@link java.lang.Exception}
     */
    public UserPrincipal getOwner(String path) throws Exception;

    /**
     * リソースの所有者を変更する。
     * 
     * @param path  操作対象とするリソースのパスを指定する。
     * @param owner 新しい所有者として設定するユーザ名を指定する。
     * @return status
     *         <ul>
     *         <li>true: 所有者が変更されたことを表す。</li>
     *         <li>false: 所有者が変更されなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public boolean setOwner(String path, String owner) throws Exception;
}
