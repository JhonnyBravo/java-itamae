package java_itamae.domain.component.directory;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/** {@link DirectoryComponent} の単体テスト。 */
@RunWith(Suite.class)
@SuiteClasses({EmptyDirectory.class, NotEmptyDirectory.class, NotExistDirectory.class})
public class DirectoryComponentTest {}
