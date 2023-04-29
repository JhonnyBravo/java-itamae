package java_itamae.domain.component.directory;

import java_itamae.domain.component.common.BaseComponent;
import java_itamae.domain.model.status.Status;

/** ディレクトリの操作を管理する。 */
public interface DirectoryComponent extends BaseComponent {
  /**
   * ディレクトリを作成する。
   *
   * <ol>
   *   <li>変数 status へ {@link Status#INIT} を設定する。
   *   <li>引数 path に指定されたディレクトリのパスが存在するか確認する。
   *       <ul>
   *         <li>存在する場合、後続処理へ遷移する。
   *         <li>存在しない場合、引数 recursive の値を確認する。
   *             <ul>
   *               <li>true の場合、親ディレクトリも含めてディレクトリを一括作成し、変数 status へ {@link Status#DONE} を設定する。
   *               <li>false の場合、単一階層のディレクトリを作成し、変数 status へ {@link Status#DONE} を設定する。
   *             </ul>
   *         <li>ディレクトリの作成中に例外が発生した場合、例外を投げて異常終了する。
   *       </ul>
   *   <li>変数 status を返り値として返す。
   * </ol>
   *
   * @param path 操作対象とするディレクトリのパスを指定する。
   * @param recursive 再帰的な操作を実行するかどうかを真偽値で指定する。
   *     <ul>
   *       <li>true: 親ディレクトリも含めてディレクトリを一括作成する。
   *       <li>false: 単一階層のディレクトリを作成する。親ディレクトリが存在しない場合は異常終了する。
   *     </ul>
   *
   * @return status {@link Status} を返す。
   * @throws Exception ディレクトリの作成中に発生した例外を投げる。
   */
  Status create(String path, boolean recursive) throws Exception;

  /**
   * ディレクトリを削除する。
   *
   * <ol>
   *   <li>変数 status へ {@link Status#INIT} を設定する。
   *   <li>引数 path に指定されたディレクトリのパスが存在するか確認する。
   *       <ul>
   *         <li>存在する場合、引数 recursive の値を確認する。
   *             <ul>
   *               <li>true の場合、親ディレクトリも含めてディレクトリを一括削除し、変数 status へ {@link Status#DONE} を設定する。
   *               <li>false の場合、単一階層のディレクトリを削除し、変数 status へ {@link Status#DONE} を設定する。
   *             </ul>
   *         <li>存在しない場合、後続処理へ遷移する。
   *         <li>ディレクトリの削除中に例外が発生した場合、例外を投げて異常終了する。
   *       </ul>
   *   <li>変数 status を返り値として返す。
   * </ol>
   *
   * @param path 操作対象とするディレクトリのパスを指定する。
   * @param recursive 再帰的な操作を実行するかどうかを真偽値で指定する。
   *     <ul>
   *       <li>true: ディレクトリを一括削除する。ディレクトリ配下にファイルが存在する場合は一緒に削除される。
   *       <li>false: ディレクトリを削除する。ディレクトリ配下にファイルが存在する場合は異常終了する。
   *     </ul>
   *
   * @return status {@link Status} を返す。
   * @throws Exception ディレクトリの削除中に発生した例外を投げる。
   */
  Status delete(String path, boolean recursive) throws Exception;
}
