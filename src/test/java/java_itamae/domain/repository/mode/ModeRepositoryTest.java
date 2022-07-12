package java_itamae.domain.repository.mode;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/** {@link ModeRepository} の単体テスト。 */
@RunWith(Suite.class)
@SuiteClasses({
  UpdateDirectoryMode.class,
  UpdateFileMode.class,
  ErrorCases1.class,
  ErrorCases2.class
})
public class ModeRepositoryTest {}
