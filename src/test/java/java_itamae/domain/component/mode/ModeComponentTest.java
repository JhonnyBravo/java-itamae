package java_itamae.domain.component.mode;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/** {@link ModeComponent} の単体テスト。 */
@RunWith(Suite.class)
@SuiteClasses({
  UpdateDirectoryMode.class,
  UpdateFileMode.class,
  ErrorCases1.class,
  ErrorCases2.class
})
public class ModeComponentTest {}
