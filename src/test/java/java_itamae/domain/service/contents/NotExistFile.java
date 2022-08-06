package java_itamae.domain.service.contents;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java_itamae.domain.common.GetTestContents;
import java_itamae.domain.model.contents.ContentsModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** ファイルが存在しない場合のテスト */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NotExistFile {
  private File file;
  @Autowired private GetTestContents getTestContents;
  @Autowired private ContentsService service;

  @Before
  public void setUp() throws Exception {
    file = new File("NotExist.txt");

    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    service.init(model);
  }

  /** {@link ContentsService#getContents()} 実行時に {@link FileNotFoundException} が送出されること。 */
  @Test(expected = FileNotFoundException.class)
  public void cse001() throws Exception {
    try {
      service.getContents();
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   * {@link ContentsService#deleteContents()} 実行時に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void cse002() throws Exception {
    final int status = service.deleteContents();
    assertThat(status, is(1));
    assertThat(file.isFile(), is(false));
  }

  /**
   * {@link ContentsService#updateContents(List)} 実行時に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void cse003() throws Exception {
    final int status = service.updateContents(getTestContents.get());
    assertThat(status, is(1));
    assertThat(file.isFile(), is(false));
  }
}
