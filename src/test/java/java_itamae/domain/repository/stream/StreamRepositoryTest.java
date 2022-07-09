package java_itamae.domain.repository.stream;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/** {@link StreamRepository} の単体テスト。 */
@RunWith(Suite.class)
@SuiteClasses({ExistFile.class, NotExistFile.class})
public class StreamRepositoryTest {}
