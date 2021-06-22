package java_itamae.domain.repository.directory;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * {@link DirectoryRepository} のテスト
 * @see NotExistDirectory
 * @see EmptyDirectory
 * @see NotEmptyDirectory
 */
@RunWith(Suite.class)
@SuiteClasses({NotExistDirectory.class, EmptyDirectory.class, NotEmptyDirectory.class})
public class TestDirectoryRepository {
}
