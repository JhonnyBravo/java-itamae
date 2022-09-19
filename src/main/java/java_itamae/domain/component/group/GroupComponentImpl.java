package java_itamae.domain.component.group;

import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserPrincipalLookupService;

public class GroupComponentImpl implements GroupComponent {
  @Override
  public GroupPrincipal createGroup(final String group) throws Exception {
    final UserPrincipalLookupService upls =
        FileSystems.getDefault().getUserPrincipalLookupService();
    return upls.lookupPrincipalByGroupName(group);
  }

  @Override
  public GroupPrincipal getGroup(final String path) throws Exception {
    final Path convertedPath = this.convertToPath(path);

    if (!convertedPath.toFile().exists()) {
      throw new FileNotFoundException(path + " が見つかりません。");
    }

    final PosixFileAttributeView pfav =
        Files.getFileAttributeView(convertedPath, PosixFileAttributeView.class);
    return pfav.readAttributes().group();
  }

  @Override
  public int updateGroup(final String path, final String group) {
    int status = 0;

    try {
      final GroupPrincipal curGroup = getGroup(path);
      final GroupPrincipal newGroup = createGroup(group);

      if (!curGroup.equals(newGroup)) {
        this.getLogger().info("グループ所有者を変更しています......");

        final Path convertedPath = this.convertToPath(path);
        final PosixFileAttributeView pfav =
            Files.getFileAttributeView(convertedPath, PosixFileAttributeView.class);
        pfav.setGroup(newGroup);

        status = 2;
      }
    } catch (final Exception e) {
      this.getLogger().warn(e.toString());
      status = 1;
    }

    return status;
  }
}
