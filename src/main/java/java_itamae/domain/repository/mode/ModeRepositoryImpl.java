package java_itamae.domain.repository.mode;

import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModeRepositoryImpl implements ModeRepository {
  private final Logger logger;

  public ModeRepositoryImpl() {
    logger = LoggerFactory.getLogger(this.getClass());
  }

  @Override
  public Set<PosixFilePermission> createMode(String mode) throws Exception {
    final String converted = mode.replaceAll("0", "---").replaceAll("1", "--x")
        .replaceAll("2", "-w-").replaceAll("3", "-wx").replaceAll("4", "r--").replaceAll("5", "r-x")
        .replaceAll("6", "rw-").replaceAll("7", "rwx");
    return PosixFilePermissions.fromString(converted);
  }

  @Override
  public Set<PosixFilePermission> getMode(String path) throws Exception {
    final Path p = FileSystems.getDefault().getPath(path);

    if (!p.toFile().exists()) {
      throw new FileNotFoundException(path + " が見つかりません。");
    }

    return Files.getPosixFilePermissions(p);
  }

  @Override
  public boolean updateMode(String path, String mode) throws Exception {
    boolean status = false;

    final Set<PosixFilePermission> curPermission = getMode(path);
    final Set<PosixFilePermission> newPermission = createMode(mode);

    if (curPermission.equals(newPermission)) {
      return status;
    }

    logger.info("パーミッションを変更しています......");
    final Path p = FileSystems.getDefault().getPath(path);
    Files.setPosixFilePermissions(p, newPermission);

    status = true;
    return status;
  }
}
