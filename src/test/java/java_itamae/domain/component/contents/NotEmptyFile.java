package java_itamae.domain.component.contents;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java_itamae.domain.common.GetTestContents;
import java_itamae.domain.common.GetTestEncoding;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.status.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ファイルが空ではない場合のテスト。 */
public class NotEmptyFile {
  private ContentsComponent component;
  private Path path;
  private GetTestEncoding getTestEncoding;
  private GetTestContents getTestContents;

  @Before
  public void setUp() throws Exception {
    getTestEncoding = new GetTestEncoding();
    getTestContents = new GetTestContents();

    final ContentsModel model = new ContentsModel();
    model.setPath("test.txt");

    component = new ContentsComponentImpl();
    path = component.convertToPath(model.getPath());

    Files.createFile(path);
    component.updateContents(model, getTestContents.get());
  }

  @After
  public void tearDown() throws Exception {
    Files.delete(path);
  }

  /**
   * 操作対象とするテキストファイルの内容が空ではない場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link ContentsComponent#getContents(ContentsModel)} 実行時に返される {@link List} の内容確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として返される {@link List} の要素数と、テキストファイルの行数が一致すること。
   *   <li>返り値として返される {@link List} に格納された文字列群がテスト用データと一致すること。
   * </ul>
   */
  @Test
  public void crs001() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());

    final List<String> newContents = getTestContents.get();
    final List<String> curContents = component.getContents(model);
    assertThat(curContents.size(), is(2));

    for (int i = 0; i < curContents.size(); i++) {
      assertThat(curContents.get(i), is(newContents.get(i)));
      System.out.println(curContents.get(i));
    }
  }

  /**
   * 操作対象とするテキストファイルの内容が空ではない場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link ContentsComponent#updateContents(ContentsModel, List)} 実行後の返り値の確認。
   *   <li>テキストファイルへの書込みが想定通りに行われていることの確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>テキストファイルへの書込みが想定通りの行数行われていること。
   *   <li>テキストファイルへ書き込まれた文字列群がテスト用データと一致すること。
   * </ul>
   */
  @Test
  public void crs002() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());

    final List<String> newContents = new ArrayList<>();
    newContents.add("上書きテスト");

    final Status status = component.updateContents(model, newContents);
    assertThat(status, is(Status.DONE));

    final List<String> curContents = component.getContents(model);
    assertThat(curContents.size(), is(1));

    for (int i = 0; i < curContents.size(); i++) {
      assertThat(curContents.get(i), is(newContents.get(i)));
      System.out.println(curContents.get(i));
    }
  }

  /**
   * 文字エンコーディングを指定して {@link ContentsComponent#updateContents(ContentsModel, List)}
   * を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>テキストファイルへの書込みが想定通り行われていることの確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>適すファイルへの書込みが想定通りの行数行われていること。
   *   <li>テキストファイルへ書き込まれた文字列群がテスト用データと一致すること。
   * </ul>
   */
  @Test
  public void crs003() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());
    model.setEncoding(getTestEncoding.get());

    final List<String> newContents = new ArrayList<>();
    newContents.add("上書きテスト");

    final Status status = component.updateContents(model, newContents);
    assertThat(status, is(Status.DONE));

    final List<String> curContents = component.getContents(model);
    assertThat(curContents.size(), is(1));

    for (int i = 0; i < curContents.size(); i++) {
      assertThat(curContents.get(i), is(newContents.get(i)));
      System.out.println(curContents.get(i));
    }
  }

  /**
   * 操作対象とするテキストファイルの内容が空ではない場合に {@link ContentsComponent#deleteContents(ContentsModel)}
   * を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link ContentsComponent#deleteContents(ContentsModel)} 実行後の返り値の確認。
   *   <li>テキストファイルの内容確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>ファイルの内容が空であること。
   * </ul>
   */
  @Test
  public void crs004() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());

    final Status status = component.deleteContents(model);
    assertThat(status, is(Status.DONE));

    final List<String> contents = component.getContents(model);
    assertThat(contents.size(), is(0));
  }
}
