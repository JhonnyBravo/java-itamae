package java_itamae.domain.repository.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ファイルが存在しない場合のテスト */
public class NotExistFile {
  private FileRepository fr;
  private File file;

  @Before
  public void setUp() throws Exception {
    fr = new FileRepositoryImpl();
    file = new File("test.txt");
  }

  @After
  public void tearDown() throws Exception {
    if (file.isFile()) {
      file.delete();
    }
  }

  /**
   *
   *
   * <ul>
   *   <li>ファイルが作成されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void frs001() throws Exception {
    final boolean status = fr.create("test.txt");
    assertThat(status, is(true));
    assertThat(file.isFile(), is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>何も実行しないこと。
   *   <li>終了ステータスが false であること。
   * </ul>
   */
  @Test
  public void frs002() throws Exception {
    final boolean status = fr.delete("test.txt");
    assertThat(status, is(false));
  }
}
