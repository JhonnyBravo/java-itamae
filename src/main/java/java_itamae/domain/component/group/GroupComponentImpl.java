package java_itamae.domain.component.group;

import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserPrincipalLookupService;
import java_itamae.domain.model.status.Status;
import org.springframework.stereotype.Service;

@Service
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
  public Status updateGroup(final String path, final String group) throws Exception {
    Status status = Status.INIT;

    final GroupPrincipal curGroup = getGroup(path);
    final GroupPrincipal newGroup = createGroup(group);

    if (!curGroup.equals(newGroup)) {
      final Path convertedPath = this.convertToPath(path);
      final PosixFileAttributeView pfav =
          Files.getFileAttributeView(convertedPath, PosixFileAttributeView.class);
      pfav.setGroup(newGroup);

      status = Status.DONE;
      this.getLogger().info("modified group: {}", group);
    }

    return status;
  }
}
