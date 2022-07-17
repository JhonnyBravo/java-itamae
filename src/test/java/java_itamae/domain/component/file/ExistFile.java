package java_itamae.domain.component.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ファイルが既に存在する場合のテスト */
public class ExistFile {
  private FileComponent component;
  private Path path;

  @Before
  public void setUp() throws Exception {
    component = new FileComponentImpl();
    path = component.convertToPath("test.txt");
    Files.createFile(path);
  }

  @After
  public void tearDown() throws Exception {
    if (path.toFile().isFile()) {
      Files.delete(path);
    }
  }

  /**
   *
   *
   * <ul>
   *   <li>何も実行されないこと。
   *   <li>終了ステータスが 0 であること。
   * </ul>
   */
  @Test
  public void frs001() throws Exception {
    final int status = component.create(path.toFile().getPath());
    assertThat(status, is(0));
  }

  /**
   *
   *
   * <ul>
   *   <li>ファイルが削除されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void frs002() throws Exception {
    final File file = path.toFile();
    final int status = component.delete(file.getPath());
    assertThat(status, is(2));
    assertThat(file.isFile(), is(false));
  }
}