package java_itamae.domain.component.mode;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java_itamae.domain.model.status.Status;

public class ModeComponentImpl implements ModeComponent {
  @Override
  public Set<PosixFilePermission> createMode(final String mode) throws Exception {
    final String converted =
        mode.replace("0", "---")
            .replace("1", "--x")
            .replace("2", "-w-")
            .replace("3", "-wx")
            .replace("4", "r--")
            .replace("5", "r-x")
            .replace("6", "rw-")
            .replace("7", "rwx");
    return PosixFilePermissions.fromString(converted);
  }

  @Override
  public Set<PosixFilePermission> getMode(final String path) throws Exception {
    final Path convertedPath = this.convertToPath(path);

    if (!convertedPath.toFile().exists()) {
      throw new FileNotFoundException(path + " が見つかりません。");
    }

    return Files.getPosixFilePermissions(convertedPath);
  }

  @Override
  public Status updateMode(final String path, final String mode) throws Exception {
    Status status = Status.INIT;

    final Set<PosixFilePermission> curPermission = getMode(path);
    final Set<PosixFilePermission> newPermission = createMode(mode);

    if (!curPermission.equals(newPermission)) {
      this.getLogger().info("パーミッションを変更しています......");
      final Path convertedPath = this.convertToPath(path);
      Files.setPosixFilePermissions(convertedPath, newPermission);
      status = Status.DONE;
    }

    return status;
  }
}
