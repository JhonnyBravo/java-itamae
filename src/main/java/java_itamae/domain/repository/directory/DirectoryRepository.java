package java_itamae.domain.repository.directory;

public interface DirectoryRepository {
    /**
     * ディレクトリを作成する。
     *
     * @param path 操作対象とするディレクトリのパスを指定する。
     * @return status
     *         <ul>
     *         <li>true: ディレクトリが作成されたことを表す。</li>
     *         <li>false: ディレクトリが作成されなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public boolean create(String path) throws Exception;

    /**
     * ディレクトリを再帰的に作成する。
     *
     * @param path 操作対象とするディレクトリのパスを指定する。
     * @return status
     *         <ul>
     *         <li>true: ディレクトリが作成されたことを表す。</li>
     *         <li>false: ディレクトリが作成されなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public boolean createRecursive(String path) throws Exception;

    /**
     * ディレクトリを削除する。
     *
     * @param path 操作対象とするディレクトリのパスを指定する。
     * @return status
     *         <ul>
     *         <li>true: ディレクトリが削除されたことを表す。</li>
     *         <li>false: ディレクトリが削除されなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public boolean delete(String path) throws Exception;

    /**
     * ディレクトリを再帰的に削除する。
     *
     * @param path 操作対象とするディレクトリのパスを指定する。
     * @return status
     *         <ul>
     *         <li>true: ディレクトリが削除されたことを表す。</li>
     *         <li>false: ディレクトリが削除されなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public boolean deleteRecursive(String path) throws Exception;
}
