package java_itamae.domain.service.contents;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import jakarta.inject.Inject;
import java.io.File;
import java.util.List;
import java_itamae.domain.common.GetTestContents;
import java_itamae.domain.component.contents.ContentsComponentImpl;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.status.Status;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/** ファイルが存在して内容が空である場合のテスト */
public class EmptyFile {
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
    file = new File("cs_test.txt");
    file.createNewFile();

    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    service.init(model);
  }

  @After
  public void tearDown() throws Exception {
    file.delete();
  }

  /**
   * 操作対象とするテキストファイルの内容が空である場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link ContentsService#getContents()} 実行後の返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link ContentsService#getContents()} 実行時に空の {@link List} が返されること。
   * </ul>
   */
  @Test
  public void css001() throws Exception {
    final List<String> contents = service.getContents();
    assertThat(contents.size(), is(0));
  }

  /**
   * 操作対象とするテキストファイルの内容が空である場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link ContentsService#deleteContents()} 実行後の返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link ContentsService#deleteContents()} 実行時に {@link Status#INIT} が返されること。
   * </ul>
   */
  @Test
  public void css002() throws Exception {
    final Status status = service.deleteContents();
    assertThat(status, is(Status.INIT));
  }

  /**
   * 操作対象とするテキストファイルの内容が空である場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link ContentsService#updateContents(List)} 実行後の返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link ContentsService#updateContents(List)} 実行時に {@link Status#DONE} が返されること。
   *   <li>ファイルに書き込まれた文字列群がテスト用データと一致すること。
   * </ul>
   */
  @Test
  public void css003() throws Exception {
    final List<String> newContents = getTestContents.get();

    final Status status = service.updateContents(newContents);
    assertThat(status, is(Status.DONE));

    final List<String> curContents = service.getContents();

    for (int i = 0; i < curContents.size(); i++) {
      assertThat(curContents.get(i), is(newContents.get(i)));
      System.out.println(curContents.get(i));
    }
  }
}
