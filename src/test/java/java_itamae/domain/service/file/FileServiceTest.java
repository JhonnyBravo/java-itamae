package java_itamae.domain.service.file;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ExistFile1.class, ExistFile2.class, NotExistFile.class, ErrorCases.class})
public class FileServiceTest {}
