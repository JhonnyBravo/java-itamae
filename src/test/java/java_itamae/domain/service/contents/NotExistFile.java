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

  /**
   * ファイルが存在しない場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link ContentsService#getContents()} 実行時に {@link FileNotFoundException} が発生すること。
   * </ul>
   */
  @Test(expected = FileNotFoundException.class)
  public void cse001() throws Exception {
    try {
      service.getContents();
    } catch (final Exception e) {
      throw e;
    }
  }

  /**
   * ファイルが存在しない場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   *   <li>ファイルの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link ContentsService#deleteContents()} 実行時に {@link FileNotFoundException} が発生すること。
   *   <li>テキストファイルが存在しないこと。
   * </ul>
   */
  @Test(expected = FileNotFoundException.class)
  public void cse002() throws Exception {
    try {
      service.deleteContents();
    } catch (Exception e) {
      assertThat(file.isFile(), is(false));
      throw e;
    }
  }

  /**
   * ファイルが存在しない場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   *   <li>ファイルの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link ContentsService#updateContents(List)} 実行時に {@link FileNotFoundException} が発生すること。
   *   <li>テキストファイルが存在しないこと。
   * </ul>
   */
  @Test(expected = FileNotFoundException.class)
  public void cse003() throws Exception {
    try {
      service.updateContents(getTestContents.get());
    } catch (Exception e) {
      assertThat(file.isFile(), is(false));
      throw e;
    }
  }
}
