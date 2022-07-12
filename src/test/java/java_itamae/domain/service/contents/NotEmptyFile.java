package java_itamae.domain.service.contents;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.util.List;
import java_itamae.domain.model.contents.ContentsModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ファイルが存在して内容が空ではない場合のテスト */
public class NotEmptyFile {
  private File file;
  private ContentsService cs;

  @Before
  public void setUp() throws Exception {
    file = new File("cs_test.txt");
    file.createNewFile();

    final ContentsModel attr = new ContentsModel();
    attr.setPath("cs_test.txt");

    cs = new ContentsServiceImpl(attr);
    cs.appendContent("1 行目");
    cs.appendContent("2 行目");
  }

  @After
  public void tearDown() throws Exception {
    file.delete();
  }

  /** {@link ContentsService#getContents()} 実行時にファイルを読込み、内容を {@link List} に変換して返すこと。 */
  @Test
  public void css001() throws Exception {
    final List<String> contents = cs.getContents();
    assertThat(contents.size(), is(2));
    assertThat(contents.get(0), is("1 行目"));
    assertThat(contents.get(1), is("2 行目"));

    for (final String line : contents) {
      System.out.println(line);
    }
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link ContentsService#deleteContents()} 実行時にファイルの内容が全削除されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void css002() throws Exception {
    final boolean status = cs.deleteContents();
    assertThat(status, is(true));

    final List<String> contents = cs.getContents();
    assertThat(contents.size(), is(0));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link ContentsService#updateContent(String)} 実行時にファイルの内容が上書きされること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void css003() throws Exception {
    final boolean status = cs.updateContent("上書きテスト");
    assertThat(status, is(true));

    final List<String> contents = cs.getContents();
    assertThat(contents.size(), is(1));
    assertThat(contents.get(0), is("上書きテスト"));

    System.out.println(contents.get(0));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link ContentsService#appendContent(String)} 実行時にファイルの末尾へ文字列が追記されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void css004() throws Exception {
    final boolean status = cs.appendContent("追記テスト");
    assertThat(status, is(true));

    final List<String> contents = cs.getContents();
    assertThat(contents.size(), is(3));
    assertThat(contents.get(2), is("追記テスト"));

    for (final String line : contents) {
      System.out.println(line);
    }
  }
}
