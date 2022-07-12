package java_itamae.domain.repository.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.nio.file.NoSuchFileException;
import org.junit.Before;
import org.junit.Test;

/** 親ディレクトリが存在しない場合のテスト */
public class NotExistParentDirectory {
  private FileRepository fr;
  private File file;

  @Before
  public void setUp() throws Exception {
    fr = new FileRepositoryImpl();
    file = new File("NotExist/test.txt");
  }

  /** {@link NoSuchFileException} が送出されること。 */
  @Test(expected = NoSuchFileException.class)
  public void fre001() throws Exception {
    try {
      fr.create("NotExist/test.txt");
    } catch (final Exception e) {
      System.err.println(e);
      assertThat(file.isFile(), is(false));
      throw e;
    }
  }
}
