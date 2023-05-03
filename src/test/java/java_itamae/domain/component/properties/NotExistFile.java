package java_itamae.domain.component.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java_itamae.domain.model.contents.ContentsModel;
import org.junit.Before;
import org.junit.Test;

/** ファイルが存在しない場合のテスト */
public class NotExistFile {
  private ContentsModel model;
  private PropertiesComponent component;
  Path path;

  @Before
  public void setUp() throws Exception {
    component = new PropertiesComponentImpl();
    path = component.convertToPath("NotExist.txt");

    model = new ContentsModel();
    model.setPath(path.toFile().getPath());
  }

  /**
   * 操作対象とするプロパティファイルが存在しない場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link PropertiesComponent#getProperties(ContentsModel)} 実行時に {@link
   *       FileNotFoundException} が発生すること。
   * </ul>
   */
  @Test(expected = FileNotFoundException.class)
  public void pre001() throws Exception {
    try {
      component.getProperties(model);
    } catch (final Exception e) {
      throw e;
    }
  }

  /**
   * 操作対象とするプロパティファイルが存在しない場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   *   <li>プロパティファイルの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link PropertiesComponent#updateProperties(ContentsModel, Map, String)} 実行時に例外が発生すること。
   *   <li>プロパティファイルが存在しないこと。
   * </ul>
   */
  @Test(expected = FileNotFoundException.class)
  public void pre002() throws Exception {
    final Map<String, String> properties = new HashMap<>();

    try {
      component.updateProperties(model, properties, path.toFile().getName());
    } catch (Exception e) {
      assertThat(path.toFile().isFile(), is(false));
      throw e;
    }
  }

  /**
   * 操作対象とするプロパティファイルが存在しない場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   *   <li>プロパティファイルの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link PropertiesComponent#deleteProperties(ContentsModel, String)} 実行時に例外が発生すること。
   *   <li>プロパティファイルが存在しないこと。
   * </ul>
   */
  @Test(expected = FileNotFoundException.class)
  public void pre003() throws Exception {
    try {
      component.deleteProperties(model, path.toFile().getName());
    } catch (Exception e) {
      assertThat(path.toFile().isFile(), is(false));
      throw e;
    }
  }
}
