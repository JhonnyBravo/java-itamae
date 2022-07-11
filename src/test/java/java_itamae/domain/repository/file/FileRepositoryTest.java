package java_itamae.domain.repository.file;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/** {@link java_itamae.domain.repository.file.FileRepository} の単体テスト。 */
@RunWith(Suite.class)
@SuiteClasses({ExistFile.class, NotExistFile.class, NotExistParentDirectory.class})
public class FileRepositoryTest {}
