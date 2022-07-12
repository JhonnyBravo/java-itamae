package java_itamae.domain.service.contents;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java_itamae.domain.model.contents.ContentsModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ファイルが存在しない場合のテスト */
public class NotExistFile {
  private File file;
  private ContentsService cs;

  @Before
  public void setUp() throws Exception {
    file = new File("cs_test.txt");

    final ContentsModel attr = new ContentsModel();
    attr.setPath("cs_test.txt");
    cs = new ContentsServiceImpl(attr);
  }

  @After
  public void tearDown() throws Exception {
    if (file.isFile()) {
      file.delete();
    }
  }

  /** {@link ContentsService#getContents()} 実行時に {@link FileNotFoundException} が送出されること。 */
  @Test(expected = FileNotFoundException.class)
  public void cse001() throws Exception {
    try {
      cs.getContents();
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /** {@link ContentsService#deleteContents()} 実行時に {@link FileNotFoundException} が送出されること。 */
  @Test(expected = FileNotFoundException.class)
  public void cse002() throws Exception {
    try {
      cs.deleteContents();
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link ContentsService#updateContent(String)} 実行時にファイルが作成されること。
   *   <li>ファイルへ文字列が書き込まれること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void css001() throws Exception {
    final boolean status = cs.updateContent("書込みテスト");
    assertThat(status, is(true));
    assertThat(file.isFile(), is(true));

    final List<String> contents = cs.getContents();
    assertThat(contents.size(), is(1));
    assertThat(contents.get(0), is("書込みテスト"));

    System.out.println(contents.get(0));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link ContentsService#appendContent(String)} 実行時にファイルが作成されること。
   *   <li>ファイルへ文字列が書き込まれること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void css002() throws Exception {
    final boolean status = cs.appendContent("追記テスト");
    assertThat(status, is(true));
    assertThat(file.isFile(), is(true));

    final List<String> contents = cs.getContents();
    assertThat(contents.size(), is(1));
    assertThat(contents.get(0), is("追記テスト"));

    System.out.println(contents.get(0));
  }
}
