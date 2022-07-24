package java_itamae.domain.component.file;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/** {@link FileComponent} の単体テスト。 */
@RunWith(Suite.class)
@SuiteClasses({ExistFile.class, NotExistFile.class, NotExistParentDirectory.class})
public class FileComponentTest {}
