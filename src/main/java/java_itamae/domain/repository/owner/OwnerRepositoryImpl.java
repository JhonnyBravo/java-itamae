package java_itamae.domain.repository.owner;

import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OwnerRepositoryImpl implements OwnerRepository {
  private final Logger logger;

  public OwnerRepositoryImpl() {
    logger = LoggerFactory.getLogger(this.getClass());
  }

  @Override
  public UserPrincipal createOwner(String owner) throws Exception {
    final UserPrincipalLookupService upls =
        FileSystems.getDefault().getUserPrincipalLookupService();
    return upls.lookupPrincipalByName(owner);
  }

  @Override
  public UserPrincipal getOwner(String path) throws Exception {
    final Path p = FileSystems.getDefault().getPath(path);

    if (!p.toFile().exists()) {
      throw new FileNotFoundException(path + " が見つかりません。");
    }

    return Files.getOwner(p);
  }

  @Override
  public boolean setOwner(String path, String owner) throws Exception {
    boolean status = false;

    final UserPrincipal curOwner = getOwner(path);
    final UserPrincipal newOwner = createOwner(owner);

    if (curOwner.equals(newOwner)) {
      return status;
    }

    logger.info("所有者を変更しています......");
    final Path p = FileSystems.getDefault().getPath(path);
    Files.setOwner(p, newOwner);

    status = true;
    return status;
  }
}
