package java_itamae.domain.service.contents;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.util.List;
import java_itamae.domain.common.GetTestContents;
import java_itamae.domain.model.contents.ContentsModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ファイルが存在して内容が空である場合のテスト */
public class EmptyFile {
  private File file;
  private GetTestContents getTestContents;
  private ContentsService service;

  @Before
  public void setUp() throws Exception {
    getTestContents = new GetTestContents();

    file = new File("cs_test.txt");
    file.createNewFile();

    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    service = new ContentsServiceImpl();
    service.init(model);
  }

  @After
  public void tearDown() throws Exception {
    file.delete();
  }

  /** {@link ContentsService#getContents()} 実行時に空の {@link List} が返されること。 */
  @Test
  public void css001() throws Exception {
    final List<String> contents = service.getContents();
    assertThat(contents.size(), is(0));
  }

  /** {@link ContentsService#deleteContents()} 実行時に終了ステータスが 0 であること。 */
  @Test
  public void css002() throws Exception {
    final int status = service.deleteContents();
    assertThat(status, is(0));
  }

  /**
   * {@link ContentsService#updateContents(List)} 実行時に
   *
   * <ul>
   *   <li>ファイルへの書込みができること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void css003() throws Exception {
    final List<String> newContents = getTestContents.get();

    final int status = service.updateContents(newContents);
    assertThat(status, is(2));

    final List<String> curContents = service.getContents();

    for (int i = 0; i < curContents.size(); i++) {
      assertThat(curContents.get(i), is(newContents.get(i)));
      System.out.println(curContents.get(i));
    }
  }
}
