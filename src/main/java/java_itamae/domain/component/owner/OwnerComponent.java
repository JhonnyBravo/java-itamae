package java_itamae.domain.component.owner;

import java.io.FileNotFoundException;
import java.nio.file.attribute.UserPrincipal;
import java_itamae.domain.component.common.BaseComponent;
import java_itamae.domain.model.status.Status;

/** ファイル/ディレクトリの所有者を管理する。 */
public interface OwnerComponent extends BaseComponent {
  /**
   * 引数 owner に指定されたユーザ名を基に {@link UserPrincipal} を生成する。
   *
   * @param owner 新しい所有者として設定するユーザ名を指定する。
   * @return owner {@link UserPrincipal} を生成して返す。
   * @throws Exception {@link UserPrincipal} 生成中に発生した例外を投げる。
   */
  UserPrincipal createOwner(String owner) throws Exception;

  /**
   * {@link UserPrincipal} を取得する。
   *
   * <ol>
   *   <li>引数 path に指定されたパスが存在するか確認する。
   *       <ul>
   *         <li>存在する場合、現在設定されている {@link UserPrincipal} を取得して返す。
   *         <li>存在しない場合、 {@link FileNotFoundException} を発生させる。
   *       </ul>
   * </ol>
   *
   * @param path 操作対象とするファイル/ディレクトリのパスを指定する。
   * @return owner 現在の所有者として設定されている {@link UserPrincipal} を取得する。
   * @throws Exception {@link UserPrincipal} 取得中に発生した例外を投げる。
   */
  UserPrincipal getOwner(String path) throws Exception;

  /**
   * ファイル/ディレクトリの所有者を変更する。
   *
   * <ol>
   *   <li>変数 status へ {@link Status#INIT} を設定する。
   *   <li>{@link OwnerComponent#getOwner(String)} を実行し、現在設定されている {@link UserPrincipal} を変数 curOwner
   *       へ設定する。
   *   <li>{@link OwnerComponent#createOwner(String)} を実行し、新しい所有者として設定する {@link UserPrincipal} を変数
   *       newOwner へ設定する。
   *   <li>変数 curOwner と変数 newOwner を比較する。
   *       <ul>
   *         <li>同一である場合、後続処理へ遷移する。
   *         <li>同一ではない場合、引数 path へ指定したパスの所有者を変数 newOwner に設定された {@link UserPrincipal} へ更新し、変数
   *             status へ {@link Status#DONE} を設定する。
   *       </ul>
   *   <li>返り値として変数 status を返す。
   * </ol>
   *
   * @param path 操作対象とするファイル/ディレクトリのパスを指定する。
   * @param owner 新しい所有者として設定するユーザ名を指定する。
   * @return status {@link Status} を返す。
   * @throws Exception 所有者の変更中に発生した例外を投げる。
   */
  Status updateOwner(String path, String owner) throws Exception;
}
