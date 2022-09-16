package java_itamae.domain.component.owner;

import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import org.springframework.stereotype.Service;

@Service
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
  public int updateOwner(final String path, final String owner) {
    int status = 0;

    try {
      final UserPrincipal curOwner = getOwner(path);
      final UserPrincipal newOwner = createOwner(owner);

      if (!curOwner.equals(newOwner)) {
        this.getLogger().info("所有者を変更しています......");
        final Path convertedPath = this.convertToPath(path);
        Files.setOwner(convertedPath, newOwner);

        status = 2;
      }
    } catch (final Exception e) {
      this.getLogger().warn(e.toString());
      status = 1;
    }

    return status;
  }
}
