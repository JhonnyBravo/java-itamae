package java_itamae.domain.component.mode;

import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import java_itamae.domain.component.common.BaseComponent;

/** ファイル/ディレクトリのパーミッション設定を管理する。 */
public interface ModeComponent extends BaseComponent {
  /**
   * {@link PosixFilePermission} の {@link Set} を生成する。
   *
   * @param mode 新しく設定するパーミッション値を指定する。
   * @return mode {@link PosixFilePermission} の {@link Set} を生成して返す。
   * @throws Exception {@link java.lang.Exception}
   */
  Set<PosixFilePermission> createMode(String mode) throws Exception;

  /**
   * {@link PosixFilePermission} の {@link Set} を取得する。
   *
   * @param path 操作対象とするファイル/ディレクトリのパスを指定する。
   * @return mode 現在のパーミッション値として設定されている {@link PosixFilePermission} の {@link Set} を取得する。
   * @throws Exception {@link java.lang.Exception}
   */
  Set<PosixFilePermission> getMode(String path) throws Exception;

  /**
   * ファイル/ディレクトリのパーミッション値を変更する。
   *
   * @param path 操作対象とするファイル/ディレクトリのパスを指定する。
   * @param mode 新しく設定するパーミッション値を指定する。
   * @return status
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: パーミッション設定値を変更して正常終了したことを表す。
   *     </ul>
   */
  int updateMode(String path, String mode);
}
