package java_itamae.domain.repository.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.File;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * ファイルが存在する場合のテスト
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(FileRepositoryImpl.class)
public class ExistFile {
  @Inject
  private FileRepository repository;
  private File file;

  @Before
  public void setUp() throws Exception {
    file = new File("test.txt");
    file.createNewFile();
  }

  @After
  public void tearDown() throws Exception {
    if (file.isFile()) {
      file.delete();
    }
  }

  /**
   * {@link FileRepository#create(String)} 実行時に何も実行せず、終了ステータスが false であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test2_1() throws Exception {
    final boolean status = repository.create("test.txt");
    assertThat(status, is(false));
  }

  /**
   * ファイルを削除できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test2_2() throws Exception {
    final boolean status = repository.delete("test.txt");
    assertThat(status, is(true));
    assertThat(file.isFile(), is(false));
  }
}
