package java_itamae.domain.repository.file;

public interface FileRepository {
    /**
     * ファイルを作成する。
     * 
     * @param path 操作対象とするファイルのパスを指定する。
     * @return status
     *         <ul>
     *         <li>true: ファイルが作成されたことを表す。</li>
     *         <li>false: ファイルが作成されなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public boolean create(String path) throws Exception;

    /**
     * ファイルを削除する。
     * 
     * @param path 操作対象とするファイルのパスを指定する。
     * @return status
     *         <ul>
     *         <li>true: ファイルが削除されたことを表す。</li>
     *         <li>false: ファイルが削除されなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public boolean delete(String path) throws Exception;
}
