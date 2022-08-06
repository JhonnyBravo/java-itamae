package java_itamae.domain.component.mode;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class ModeComponentImpl implements ModeComponent {
  @Override
  public Set<PosixFilePermission> createMode(String mode) throws Exception {
    final String converted =
        mode.replaceAll("0", "---")
            .replaceAll("1", "--x")
            .replaceAll("2", "-w-")
            .replaceAll("3", "-wx")
            .replaceAll("4", "r--")
            .replaceAll("5", "r-x")
            .replaceAll("6", "rw-")
            .replaceAll("7", "rwx");
    return PosixFilePermissions.fromString(converted);
  }

  @Override
  public Set<PosixFilePermission> getMode(String path) throws Exception {
    final Path p = this.convertToPath(path);

    if (!p.toFile().exists()) {
      throw new FileNotFoundException(path + " が見つかりません。");
    }

    return Files.getPosixFilePermissions(p);
  }

  @Override
  public int updateMode(String path, String mode) {
    int status = 0;

    try {
      final Set<PosixFilePermission> curPermission = getMode(path);
      final Set<PosixFilePermission> newPermission = createMode(mode);

      if (curPermission.equals(newPermission)) {
        return status;
      }

      this.getLogger().info("パーミッションを変更しています......");
      final Path p = this.convertToPath(path);
      Files.setPosixFilePermissions(p, newPermission);

      status = 2;
    } catch (final Exception e) {
      this.getLogger().warn(e.toString());
      status = 1;
    }

    return status;
  }
}
