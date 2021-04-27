package java_itamae.domain.repository.group;

import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserPrincipalLookupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroupRepositoryImpl implements GroupRepository {
  private final Logger logger;

  public GroupRepositoryImpl() {
    logger = LoggerFactory.getLogger(this.getClass());
  }

  @Override
  public GroupPrincipal createGroup(String group) throws Exception {
    final UserPrincipalLookupService upls =
        FileSystems.getDefault().getUserPrincipalLookupService();
    return upls.lookupPrincipalByGroupName(group);
  }

  @Override
  public GroupPrincipal getGroup(String path) throws Exception {
    final Path p = FileSystems.getDefault().getPath(path);

    if (!p.toFile().exists()) {
      throw new FileNotFoundException(path + " が見つかりません。");
    }

    final PosixFileAttributeView pfav = Files.getFileAttributeView(p, PosixFileAttributeView.class);
    return pfav.readAttributes().group();
  }

  @Override
  public boolean updateGroup(String path, String group) throws Exception {
    boolean status = false;

    final GroupPrincipal curGroup = getGroup(path);
    final GroupPrincipal newGroup = createGroup(group);

    if (curGroup.equals(newGroup)) {
      return status;
    }

    logger.info("グループ所有者を変更しています......");
    final Path p = FileSystems.getDefault().getPath(path);
    final PosixFileAttributeView pfav = Files.getFileAttributeView(p, PosixFileAttributeView.class);
    pfav.setGroup(newGroup);

    status = true;
    return status;
  }
}
