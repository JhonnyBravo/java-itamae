package java_itamae.domain.component.mode;

import java.io.FileNotFoundException;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import java_itamae.domain.component.common.BaseComponent;
import java_itamae.domain.model.status.Status;

/** ファイル/ディレクトリのパーミッション設定を管理する。 */
public interface ModeComponent extends BaseComponent {
  /**
   * {@link PosixFilePermission} の {@link Set} を生成する。
   *
   * @param mode 新しく設定するパーミッション値を指定する。
   * @return mode {@link PosixFilePermission} の {@link Set} を生成して返す。
   * @throws Exception パーミッション生成中に発生した例外を投げる。
   */
  Set<PosixFilePermission> createMode(String mode) throws Exception;

  /**
   * {@link PosixFilePermission} の {@link Set} を取得する。
   *
   * <ol>
   *   <li>引数 path に指定されたパスが存在するか確認する。
   *       <ul>
   *         <li>存在する場合、現在設定されているパーミッション値を取得して返す。
   *         <li>存在しない場合、 {@link FileNotFoundException} を発生させる。
   *       </ul>
   * </ol>
   *
   * @param path 操作対象とするファイル/ディレクトリのパスを指定する。
   * @return mode 現在のパーミッション値として設定されている {@link PosixFilePermission} の {@link Set} を取得する。
   * @throws Exception パーミッション取得中に発生した例外を投げる。
   */
  Set<PosixFilePermission> getMode(String path) throws Exception;

  /**
   * ファイル/ディレクトリのパーミッション値を変更する。
   *
   * <ol>
   *   <li>変数 status へ {@link Status#INIT} を設定する。
   *   <li>{@link ModeComponent#getMode(String)} を実行し、現在設定されているパーミッション値を取得して変数 curPermission へ設定する。
   *   <li>{@link ModeComponent#createMode(String)} を実行し、新しいパーミッション値として設定する {@link
   *       PosixFilePermission} の {@link Set} を変数 nwePermission へ設定する。
   *   <li>変数 curPermission と変数 newPermission を比較する。
   *       <ul>
   *         <li>同一である場合、後続処理へ遷移する。
   *         <li>同一ではない場合、引数 path へ指定されたパスのパーミッション値を変数 newPermission に設定されたパーミッション値へ更新し、変数 status へ
   *             {@link Status#DONE} を設定する。
   *       </ul>
   *   <li>返り値として変数 status を返す。
   * </ol>
   *
   * @param path 操作対象とするファイル/ディレクトリのパスを指定する。
   * @param mode 新しく設定するパーミッション値を指定する。
   * @return status {@link Status} を返す。
   * @throws Exception パーミッション値の更新中に発生した例外を投げる。
   */
  Status updateMode(String path, String mode) throws Exception;
}
