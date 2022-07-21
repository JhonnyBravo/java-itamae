package java_itamae.domain.component.properties;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/** {@link PropertiesComponent} の単体テスト。 */
@RunWith(Suite.class)
@SuiteClasses({EmptyFile.class, NotEmptyFile.class, NotExistFile.class})
public class PropertiesComponentTest {}
