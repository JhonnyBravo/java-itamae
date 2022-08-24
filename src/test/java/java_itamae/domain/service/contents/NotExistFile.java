package java_itamae.domain.service.contents;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import jakarta.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java_itamae.domain.common.GetTestContents;
import java_itamae.domain.component.contents.ContentsComponentImpl;
import java_itamae.domain.model.contents.ContentsModel;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/** ファイルが存在しない場合のテスト */
public class NotExistFile {
  private File file;
  @Inject private GetTestContents getTestContents;
  @Inject private ContentsService service;

  @Rule
  public WeldInitiator weld =
      WeldInitiator.from(
              ContentsServiceImpl.class, ContentsComponentImpl.class, GetTestContents.class)
          .inject(this)
          .build();

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
