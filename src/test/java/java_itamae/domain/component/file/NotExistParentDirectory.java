package java_itamae.domain.component.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

/** 親ディレクトリが存在しない場合のテスト */
public class NotExistParentDirectory {
  private FileComponent component;
  private Path path;

  @Before
  public void setUp() throws Exception {
    component = new FileComponentImpl();
    path = component.convertToPath("NotExist/test.txt");
  }

  /**
   *
   *
   * <ul>
   *   <li>ファイルが作成されないこと。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void fre001() throws Exception {
    final int status = component.create(path.toFile().getPath());
    assertThat(path.toFile().isFile(), is(false));
    assertThat(status, is(1));
  }
}
