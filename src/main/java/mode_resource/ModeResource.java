package mode_resource;

import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

/**
 * ファイルまたはディレクトリのパーミッション設定を管理する。
 */
public interface ModeResource {
    /**
     * 新しいパーミッションとして設定する PosixFilePermission を生成する。
     * 
     * @param mode 新しいパーミッションとして設定するパーミッション値を 3 桁の数列で指定する。
     * @return Set&lt;PosixFilePermission&gt;
     */
    public Set<PosixFilePermission> createMode(String mode);

    /**
     * ファイルまたはディレクトリの現在のパーミッション設定を取得する。
     * 
     * @param path 操作対象とするファイルまたはディレクトリのパスを指定する。
     * @return Set&lt;PosixFilePermission&gt;
     */
    public Set<PosixFilePermission> getMode(String path);

    /**
     * ファイルまたはディレクトリのパーミッション設定を変更する。
     * 
     * @param path 操作対象とするファイルまたはディレクトリのパスを指定する。
     * @param mode 新しいパーミッションとして設定するパーミッション値を 3 桁の数列で指定する。
     */
    public void setMode(String path, String mode);
}
