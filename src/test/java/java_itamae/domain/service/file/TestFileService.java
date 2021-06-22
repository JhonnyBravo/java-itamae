package java_itamae.domain.service.file;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({FileError.class, ChangeFileStatus.class, NoChangeFileStatus.class,
    NotExistFile.class})
public class TestFileService {
}
