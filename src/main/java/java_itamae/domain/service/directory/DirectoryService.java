package java_itamae.domain.service.directory;

import java_itamae.domain.model.Attribute;

public interface DirectoryService {
    /**
     * @param recursive 再帰的な操作を実行するかどうかを真偽値で指定する。
     *                  <ul>
     *                  <li>true: ディレクトリを再帰的に作成 / 削除するように設定する。</li>
     *                  <li>false: ディレクトリを再帰的に作成 / 削除しないように設定する。(既定値)</li>
     *                  </ul>
     */
    public void setRecursive(boolean recursive);

    /**
     * ディレクトリを作成し、所有者・グループ所有者・パーミッションの設定を変更する。
     *
     * @param attr 操作対象とするディレクトリの設定情報を納めた Attribute オブジェクトを指定する。
     * @return status
     *         <p>
     *         以下の場合に true を返す。
     *         </p>
     *         <ul>
     *         <li>ディレクトリが新規作成された場合</li>
     *         <li>ディレクトリの所有者が変更された場合</li>
     *         <li>ディレクトリのグループ所有者が変更された場合</li>
     *         <li>ディレクトリのパーミッション設定が変更された場合</li>
     *         </ul>
     *
     *         <p>
     *         以下の場合に false を返す。
     *         </p>
     *         <ul>
     *         <li>ディレクトリが作成されなかった場合</li>
     *         <li>ディレクトリの所有者が変更されなかった場合</li>
     *         <li>ディレクトリのグループ所有者が変更されなかった場合</li>
     *         <li>ディレクトリのパーミッション設定が変更されなかった場合</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public boolean create(Attribute attr) throws Exception;

    /**
     * ディレクトリを削除する。
     *
     * @param attr 操作対象とするディレクトリの設定情報を納めた Attribute オブジェクトを指定する。
     * @return status
     *         <ul>
     *         <li>true: ディレクトリが削除されたことを表す。</li>
     *         <li>false: ディレクトリが削除されなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public boolean delete(Attribute attr) throws Exception;
}
