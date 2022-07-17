package java_itamae.domain.component.directory;

import java_itamae.domain.component.common.BaseComponent;

/** ディレクトリの操作を管理する。 */
public interface DirectoryComponent extends BaseComponent {
  /**
   * ディレクトリを作成する。
   *
   * @param path 操作対象とするディレクトリのパスを指定する。
   * @param recursive 再帰的な操作を実行するかどうかを真偽値で指定する。
   *     <ul>
   *       <li>true: 親ディレクトリも含めてディレクトリを一括作成する。
   *       <li>false: 単一階層のディレクトリを作成する。親ディレクトリが存在しない場合は異常終了する。
   *     </ul>
   *
   * @return status
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: ディレクトリが作成されて正常終了したことを表す。
   *     </ul>
   */
  int create(String path, boolean recursive);

  /**
   * ディレクトリを削除する。
   *
   * @param path 操作対象とするディレクトリのパスを指定する。
   * @param recursive 再帰的な操作を実行するかどうかを真偽値で指定する。
   *     <ul>
   *       <li>true: ディレクトリを一括削除する。ディレクトリ配下にファイルが存在する場合は一緒に削除される。
   *       <li>false: ディレクトリを削除する。ディレクトリ配下にファイルが存在する場合は異常終了する。
   *     </ul>
   *
   * @return status
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: ディレクトリが削除されて正常終了したことを表す。
   *     </ul>
   */
  int delete(String path, boolean recursive);
}
