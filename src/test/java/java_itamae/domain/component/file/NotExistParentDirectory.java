package java_itamae.domain.component.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Path;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** 親ディレクトリが存在しない場合のテスト */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NotExistParentDirectory {
  @Autowired private FileComponent component;
  private Path path;

  @Before
  public void setUp() throws Exception {
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
