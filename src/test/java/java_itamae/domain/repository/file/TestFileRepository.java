package java_itamae.domain.repository.file;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * {@link FileRepository} のテスト
 * @see NotExistFile
 * @see ExistFile
 */
@RunWith(Suite.class)
@SuiteClasses({NotExistFile.class, ExistFile.class})
public class TestFileRepository {
}
