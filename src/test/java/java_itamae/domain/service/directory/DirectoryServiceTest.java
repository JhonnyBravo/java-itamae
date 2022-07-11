package java_itamae.domain.service.directory;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/** {@link DirectoryService} の単体テスト。 */
@RunWith(Suite.class)
@SuiteClasses({
  ExistDirectory1.class,
  ExistDirectory2.class,
  NotExistDirectory.class,
  NotExistParentDirectory.class,
  DeleteDirectory.class,
  ErrorCases.class
})
public class DirectoryServiceTest {}
