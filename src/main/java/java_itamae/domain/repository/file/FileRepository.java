package java_itamae.domain.repository.file;

/**
 * ファイルの操作を管理する。
 */
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
  boolean create(String path) throws Exception;

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
  boolean delete(String path) throws Exception;
}
