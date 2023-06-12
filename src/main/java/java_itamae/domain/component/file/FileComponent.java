package java_itamae.domain.component.file;

import java_itamae.domain.component.common.BaseComponent;
import java_itamae.domain.model.status.Status;

/** ファイルの操作を管理する。 */
public interface FileComponent extends BaseComponent {
  /**
   * ファイルを作成する。
   *
   * <ol>
   *   <li>変数 status へ {@link Status#INIT} を設定する。
   *   <li>引数 path に指定されたファイルのパスが存在するか確認する。
   *       <ul>
   *         <li>存在する場合、後続処理へ遷移する。
   *         <li>存在しない場合、ファイルを新規作成し、変数 status へ {@link Status#DONE} を設定する。
   *         <li>ファイルの作成中に例外が発生した場合、例外を投げて異常終了する。
   *       </ul>
   *   <li>変数 status を返り値として返す。
   * </ol>
   *
   * @param path 操作対象とするファイルのパスを指定する。
   * @return status {@link Status} を返す。
   * @throws Exception ファイル作成中に発生した例外を投げる。
   */
  Status create(String path) throws Exception;

  /**
   * ファイルを削除する。
   *
   * <ol>
   *   <li>変数 status へ {@link Status#INIT} を設定する。
   *   <li>引数 path に指定されたファイルのパスが存在するか確認する。
   *       <ul>
   *         <li>存在する場合、ファイルを削除し、変数 status へ {@link Status#DONE} を設定する。
   *         <li>存在しない場合、後続処理へ遷移する。
   *         <li>ファイルの削除中に例外が発生した場合、例外を投げて異常終了する。
   *       </ul>
   *   <li>変数 status を返り値として返す。
   * </ol>
   *
   * @param path 操作対象とするファイルのパスを指定する。
   * @return status {@link Status} を返す。
   * @throws Exception ファイル削除中に発生した例外を投げる。
   */
  Status delete(String path) throws Exception;
}
