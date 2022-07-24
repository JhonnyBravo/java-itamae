package java_itamae.domain.service.contents;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({NotExistFile.class, EmptyFile.class, NotEmptyFile.class})
public class ContentsServiceTest {}
