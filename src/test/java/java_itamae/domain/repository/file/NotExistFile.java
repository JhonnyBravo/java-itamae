package java_itamae.domain.repository.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.File;
import java.nio.file.NoSuchFileException;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * ファイルが存在しない場合のテスト
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(FileRepositoryImpl.class)
public class NotExistFile {
  @Inject
  private FileRepository repository;
  private File file;

  @Before
  public void setUp() throws Exception {
    file = new File("test.txt");
  }

  @After
  public void tearDown() throws Exception {
    if (file.isFile()) {
      file.delete();
    }
  }

  /**
   * ファイルを作成できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test1() throws Exception {
    final boolean status = repository.create("test.txt");
    assertThat(status, is(true));
    assertThat(file.isFile(), is(true));
  }

  /**
   * {@link FileRepository#delete(String)} 実行時に何も実行せず、終了ステータスが false であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test2() throws Exception {
    final boolean status = repository.delete("test.txt");
    assertThat(status, is(false));
  }

  /**
   * 親ディレクトリが存在しない状態で {@link FileRepository#create(String)} を実行した場合に {@link NoSuchFilesException}
   * が送出されること。
   *
   * @throws Exception {@link Exception}
   */
  @Test(expected = NoSuchFileException.class)
  public void test3_1() throws Exception {
    final File notExist = new File("NotExist/test.txt");

    try {
      repository.create("NotExist/test.txt");
    } catch (final Exception e) {
      System.err.println(e);
      assertThat(notExist.isFile(), is(false));
      throw e;
    }
  }
}
