package java_itamae.domain.service.contents;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.util.List;
import java_itamae.domain.model.contents.ContentsModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ファイルが存在して内容が空である場合のテスト */
public class EmptyFile {
  private File file;
  private ContentsService cs;

  @Before
  public void setUp() throws Exception {
    file = new File("cs_test.txt");
    file.createNewFile();

    final ContentsModel attr = new ContentsModel();
    attr.setPath("cs_test.txt");
    cs = new ContentsServiceImpl(attr);
  }

  @After
  public void tearDown() throws Exception {
    file.delete();
  }

  /** {@link ContentsService#getContents()} 実行時に空の {@link List} が返されること。 */
  @Test
  public void css001() throws Exception {
    final List<String> contents = cs.getContents();
    assertThat(contents.size(), is(0));
  }

  /** {@link ContentsService#deleteContents()} 実行時に終了ステータスが false であること。 */
  @Test
  public void css002() throws Exception {
    final boolean status = cs.deleteContents();
    assertThat(status, is(false));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link ContentsService#updateContent(String)} 実行時にファイルへ文字列が書き込まれること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void css003() throws Exception {
    final boolean status = cs.updateContent("書込みテスト");
    assertThat(status, is(true));

    final List<String> contents = cs.getContents();
    assertThat(contents.size(), is(1));
    assertThat(contents.get(0), is("書込みテスト"));

    System.out.println(contents.get(0));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link ContentsService#appendContent(String)} 実行時にファイルへ文字列が書き込まれること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void css004() throws Exception {
    final boolean status = cs.appendContent("追記テスト");
    assertThat(status, is(true));

    final List<String> contents = cs.getContents();
    assertThat(contents.size(), is(1));
    assertThat(contents.get(0), is("追記テスト"));

    System.out.println(contents.get(0));
  }
}
