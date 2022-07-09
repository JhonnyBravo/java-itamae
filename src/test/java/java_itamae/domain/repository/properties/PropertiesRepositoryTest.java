package java_itamae.domain.repository.properties;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/** {@link PropertiesRepository} の単体テスト。 */
@RunWith(Suite.class)
@SuiteClasses({EmptyFile.class, NotEmptyFile.class})
public class PropertiesRepositoryTest {}
