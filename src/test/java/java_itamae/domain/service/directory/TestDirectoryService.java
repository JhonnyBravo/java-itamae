package java_itamae.domain.service.directory;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * {@link DirectoryService} のテスト。
 * @see DirectoryError
 * @see NoChangeDirectoryStatus
 * @see ChangeDirectoryStatus
 * @see NotExistDirectory
 * @see NotExistParentDirectory
 */
@RunWith(Suite.class)
@SuiteClasses({DirectoryError.class, NoChangeDirectoryStatus.class, ChangeDirectoryStatus.class,
    NotExistDirectory.class, NotExistParentDirectory.class})
public class TestDirectoryService {
}
