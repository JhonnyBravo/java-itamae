package java_itamae.domain.component.contents;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import jakarta.inject.Inject;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java_itamae.domain.model.contents.ContentsModel;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/** ファイルが存在しない場合のテスト */
public class NotExistFile {
  private Path path;
  private ContentsModel model;
  @Inject private ContentsComponent component;

  @Rule
  public WeldInitiator weld = WeldInitiator.from(ContentsComponentImpl.class).inject(this).build();

  @Before
  public void setUp() throws Exception {
    model = new ContentsModel();
    model.setPath("NotExist.txt");

    path = component.convertToPath(model.getPath());
  }

  /**
   * 操作対象とするテキストファイルが存在しない場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link ContentsComponent#getContents(ContentsModel)} 実行時に {@link FileNotFoundException}
   *       が発生すること。
   * </ul>
   */
  @Test(expected = FileNotFoundException.class)
  public void cre001() throws Exception {
    try {
      component.getContents(model);
    } catch (final Exception e) {
      throw e;
    }
  }

  /**
   * 操作対象とするテキストファイルが存在しない場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   *   <li>ファイルの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link ContentsComponent#updateContents(ContentsModel, List)} 実行時に {@link
   *       FileNotFoundException} が発生すること。
   *   <li>ファイルが存在しないこと。
   * </ul>
   */
  @Test(expected = FileNotFoundException.class)
  public void cre002() throws Exception {
    final List<String> contents = new ArrayList<>();
    contents.add("書込みテスト");

    try {
      component.updateContents(model, contents);
    } catch (Exception e) {
      assertThat(path.toFile().isFile(), is(false));
      throw e;
    }
  }

  /**
   * 操作対象とするテキストファイルが存在しない場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   *   <li>ファイルの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link ContentsComponent#deleteContents(ContentsModel)} 実行時に {@link
   *       FileNotFoundException} が発生すること。
   *   <li>ファイルが存在しないこと。
   * </ul>
   */
  @Test(expected = FileNotFoundException.class)
  public void cre003() throws Exception {
    try {
      component.deleteContents(model);
    } catch (Exception e) {
      assertThat(path.toFile().isFile(), is(false));
      throw e;
    }
  }
}
