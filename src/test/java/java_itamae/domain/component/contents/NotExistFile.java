package java_itamae.domain.component.contents;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import java_itamae.domain.model.contents.ContentsModel;

/** ファイルが存在しない場合のテスト */
public class NotExistFile {
  private Path path;
  private ContentsModel model;
  private ContentsComponent component;

  @Before
  public void setUp() throws Exception {
    component = new ContentsComponentImpl();

    model = new ContentsModel();
    model.setPath("NotExist.txt");

    path = component.convertToPath(model.getPath());
  }

  /**
   * {@link ContentsComponent#getContents(ContentsModel)} 実行時に {@link FileNotFoundException}
   * が送出されること。
   */
  @Test(expected = FileNotFoundException.class)
  public void cre001() throws Exception {
    try {
      component.getContents(model);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   * {@link ContentsComponent#updateContents(ContentsModel, List)} 実行時に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   *   <li>ファイルが新規作成されないこと。
   * </ul>
   */
  @Test
  public void cre002() throws Exception {
    final List<String> contents = new ArrayList<>();
    contents.add("書込みテスト");

    final int status = component.updateContents(model, contents);
    assertThat(status, is(1));
    assertThat(path.toFile().isFile(), is(false));
  }

  /**
   * {@link ContentsComponent#deleteContents(ContentsModel)} 実行時に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   *   <li>ファイルが新規作成されないこと。
   * </ul>
   */
  @Test
  public void cre003() throws Exception {
    final int status = component.deleteContents(model);
    assertThat(status, is(1));
    assertThat(path.toFile().isFile(), is(false));
  }
}
