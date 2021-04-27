package java_itamae.domain.repository.mode;

import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

/**
 * リソースのパーミッション設定を管理する。
 */
public interface ModeRepository {
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
   * @param path 操作対象とするリソースのパスを指定する。
   * @return mode 現在のパーミッション値として設定されている {@link PosixFilePermission} の {@link Set} を取得する。
   * @throws Exception {@link java.lang.Exception}
   */
  Set<PosixFilePermission> getMode(String path) throws Exception;

  /**
   * リソースのパーミッション値を変更する。
   *
   * @param path 操作対象とするリソースのパスを指定する。
   * @param mode 新しく設定するパーミッション値を指定する。
   * @return status
   *         <ul>
   *         <li>true: パーミッション値が変更されたことを表す。</li>
   *         <li>false: パーミッション値が変更されなかったことを表す。</li>
   *         </ul>
   * @throws Exception {@link java.lang.Exception}
   */
  boolean updateMode(String path, String mode) throws Exception;
}
