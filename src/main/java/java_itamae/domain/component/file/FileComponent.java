package java_itamae.domain.component.file;

import java_itamae.domain.component.common.BaseComponent;

/** ファイルの操作を管理する。 */
public interface FileComponent extends BaseComponent {
  /**
   * ファイルを作成する。
   *
   * @param path 操作対象とするファイルのパスを指定する。
   * @return status
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: ファイルを作成して正常終了したことを表す。
   *     </ul>
   */
  int create(String path);

  /**
   * ファイルを削除する。
   *
   * @param path 操作対象とするファイルのパスを指定する。
   * @return status
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: ファイルを削除して正常終了したことを表す。
   *     </ul>
   */
  int delete(String path);
}
