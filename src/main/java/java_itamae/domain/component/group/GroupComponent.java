package java_itamae.domain.component.group;

import java.io.FileNotFoundException;
import java.nio.file.attribute.GroupPrincipal;
import java_itamae.domain.component.common.BaseComponent;
import java_itamae.domain.model.status.Status;

/** ファイル/ディレクトリのグループ所有者を管理する。 */
public interface GroupComponent extends BaseComponent {
  /**
   * {@link GroupPrincipal} を生成する。
   *
   * @param group 新しいグループ所有者として設定するグループ名を指定する。
   * @return group {@link GroupPrincipal} を生成して返す。
   * @throws Exception {@link GroupPrincipal} 生成中に発生した例外を投げる。
   */
  GroupPrincipal createGroup(String group) throws Exception;

  /**
   * {@link GroupPrincipal} を取得する。
   *
   * <ol>
   *   <li>引数 path に指定したパスが存在するか確認する。
   *       <ul>
   *         <li>存在する場合、 {@link GroupPrincipal} を取得して返す。
   *         <li>存在しない場合、 {@link FileNotFoundException} を発生させる。
   *       </ul>
   * </ol>
   *
   * @param path 操作対象とするファイル/ディレクトリのパスを指定する。
   * @return group 現在のグループ所有者として設定されている {@link GroupPrincipal} を生成して返す。
   * @throws Exception {@link GroupPrincipal} 取得中に発生した例外を投げる。
   */
  GroupPrincipal getGroup(String path) throws Exception;

  /**
   * ファイル/ディレクトリのグループ所有者を変更する。
   *
   * <ol>
   *   <li>変数 status へ {@link Status#INIT} を設定する。
   *   <li>{@link GroupComponent#getGroup(String)} を実行し、現在のグループ所有者として設定されている {@link GroupPrincipal}
   *       を変数 curGroup へ設定する。
   *   <li>{@link GroupComponent#createGroup(String)} を実行し、新しいグループ所有者として設定する {@link GroupPrincipal}
   *       を変数 newGroup へ設定する。
   *   <li>変数 curGroup と変数 newGroup を比較する。
   *       <ul>
   *         <li>同一である場合、後続処理へ遷移する。
   *         <li>同一ではない場合、変数 path に指定されたパスのグループ所有者を変数 newGroup に設定された {@link GroupPrincipal} へ更新し、変数
   *             status へ {@link Status#DONE} を設定する。
   *       </ul>
   *   <li>返り値として変数 status を返す。
   * </ol>
   *
   * @param path 操作対象とするファイル/ディレクトリのパスを指定する。
   * @param group 新しいグループ所有者として設定するグループ名を指定する。
   * @return status {@link Status} を返す。
   * @throws Exception グループ所有者の変更中に発生した例外を投げる。
   */
  Status updateGroup(String path, String group) throws Exception;
}
