package java_itamae.domain.service.contents;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java_itamae.domain.common.GetTestContents;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.status.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** ファイルが存在して内容が空ではない場合のテスト */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NotEmptyFile {
  private File file;
  @Autowired private ContentsService service;
  @Autowired private GetTestContents getTestContents;

  @Before
  public void setUp() throws Exception {
    file = new File("cs_test.txt");
    file.createNewFile();

    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    service.init(model);
    service.updateContents(getTestContents.get());
  }

  @After
  public void tearDown() throws Exception {
    file.delete();
  }

  /**
   * 操作対象とするテキストファイルの内容が空ではない場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link ContentsService#getContents()} 実行後の返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link ContentsService#getContents()} から取得した {@link List} の要素数と、テキストファイルの行数が一致すること。
   *   <li>{@link ContentsService#getContents()} から取得した {@link List} の要素がテスト用データと一致すること。
   * </ul>
   */
  @Test
  public void css001() throws Exception {
    final List<String> newContents = getTestContents.get();
    final List<String> curContents = service.getContents();
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
   *   <li>{@link ContentsService#deleteContents()} 実行後の返り値の確認。
   *   <li>テキストファイルの内容確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>テキストファイルの内容が空であること。
   * </ul>
   */
  @Test
  public void css002() throws Exception {
    final Status status = service.deleteContents();
    assertThat(status, is(Status.DONE));

    final List<String> contents = service.getContents();
    assertThat(contents.size(), is(0));
  }

  /**
   * 操作対象とするテキストファイルの内容が空ではない場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link ContentsService#updateContents(List)} 実行後の返り値の確認。
   *   <li>テキストファイルの内容確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>テキストファイルの行数がテスト用データの要素数と一致すること。
   *   <li>テキストファイルの内容がテスト用データと一致すること。
   * </ul>
   */
  @Test
  public void css003() throws Exception {
    final List<String> newContents = new ArrayList<>();
    newContents.add("update");

    final Status status = service.updateContents(newContents);
    assertThat(status, is(Status.DONE));

    final List<String> curContents = service.getContents();
    assertThat(curContents.size(), is(1));

    for (int i = 0; i < curContents.size(); i++) {
      assertThat(curContents.get(i), is(newContents.get(i)));
      System.out.println(curContents.get(i));
    }
  }
}
