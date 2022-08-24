package java_itamae.domain.component.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import jakarta.inject.Inject;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/** ファイルが存在しない場合のテスト */
public class NotExistFile {
  @Inject private FileComponent component;
  private Path path;

  @Rule
  public WeldInitiator weld = WeldInitiator.from(FileComponentImpl.class).inject(this).build();

  @Before
  public void setUp() throws Exception {
    path = component.convertToPath("test.txt");
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
   *   <li>ファイルが作成されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void frs001() throws Exception {
    final File file = path.toFile();
    final int status = component.create(file.getPath());
    assertThat(status, is(2));
    assertThat(file.isFile(), is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>何も実行しないこと。
   *   <li>終了ステータスが 0 であること。
   * </ul>
   */
  @Test
  public void frs002() throws Exception {
    final int status = component.delete(path.toFile().getPath());
    assertThat(status, is(0));
  }
}
