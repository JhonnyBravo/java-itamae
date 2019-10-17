package java_itamae.domain.service.file;

import java_itamae.domain.model.Attribute;

public interface FileService {
    /**
     * ファイルを作成し、所有者・グループ所有者・パーミッションの設定を変更する。
     *
     * @param attr 操作対象とするファイルの設定情報を納めた Attribute オブジェクトを指定する。
     * @return status
     *         <p>
     *         以下の場合に true を返す。
     *         </p>
     *         <ul>
     *         <li>ファイルが新規作成された場合</li>
     *         <li>ファイルの所有者が変更された場合</li>
     *         <li>ファイルのグループ所有者が変更された場合</li>
     *         <li>ファイルのパーミッション設定が変更された場合</li>
     *         </ul>
     *
     *         <p>
     *         以下の場合に false を返す。
     *         </p>
     *         <ul>
     *         <li>ファイルが作成されなかった場合</li>
     *         <li>ファイルの所有者が変更されなかった場合</li>
     *         <li>ファイルのグループ所有者が変更されなかった場合</li>
     *         <li>ファイルのパーミッション設定が変更されなかった場合</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public boolean create(Attribute attr) throws Exception;

    /**
     * ファイルを削除する。
     *
     * @param attr 操作対象とするファイルの設定情報を納めた Attribute オブジェクトを指定する。
     * @return status
     *         <ul>
     *         <li>true: ファイルが削除されたことを表す。</li>
     *         <li>false: ファイルが削除されなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public boolean delete(Attribute attr) throws Exception;
}
