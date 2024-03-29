package java_itamae.domain.component.owner;

import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java_itamae.domain.model.status.Status;

public class OwnerComponentImpl implements OwnerComponent {
  @Override
  public UserPrincipal createOwner(final String owner) throws Exception {
    final UserPrincipalLookupService upls =
        FileSystems.getDefault().getUserPrincipalLookupService();
    return upls.lookupPrincipalByName(owner);
  }

  @Override
  public UserPrincipal getOwner(final String path) throws Exception {
    final Path convertedPath = FileSystems.getDefault().getPath(path);

    if (!convertedPath.toFile().exists()) {
      throw new FileNotFoundException(path + " が見つかりません。");
    }

    return Files.getOwner(convertedPath);
  }

  @Override
  public Status updateOwner(final String path, final String owner) throws Exception {
    Status status = Status.INIT;

    final UserPrincipal curOwner = getOwner(path);
    final UserPrincipal newOwner = createOwner(owner);

    if (!curOwner.equals(newOwner)) {
      final Path convertedPath = this.convertToPath(path);
      Files.setOwner(convertedPath, newOwner);

      status = Status.DONE;
      this.getLogger().info("modified owner: {}", owner);
    }

    return status;
  }
}
