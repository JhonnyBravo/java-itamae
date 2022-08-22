package java_itamae.domain.component.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java_itamae.domain.model.contents.ContentsModel;
import javax.inject.Inject;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/** ファイルが存在しない場合のテスト */
public class NotExistFile {
  @Inject private PropertiesComponent component;
  private ContentsModel model;
  Path path;

  @Rule
  public WeldInitiator weld =
      WeldInitiator.from(PropertiesComponentImpl.class).inject(this).build();

  @Before
  public void setUp() throws Exception {
    path = component.convertToPath("NotExist.txt");

    model = new ContentsModel();
    model.setPath(path.toFile().getPath());
  }

  /**
   * {@link PropertiesComponent#getProperties(ContentsModel)} 実行時に {@link FileNotFoundException}
   * が送出されること。
   */
  @Test(expected = FileNotFoundException.class)
  public void pre001() throws Exception {
    try {
      component.getProperties(model);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   * {@link PropertiesComponent#updateProperties(ContentsModel, Map, String)} 実行時に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   *   <li>ファイルが作成されないこと。
   * </ul>
   */
  @Test
  public void pre002() throws Exception {
    final Map<String, String> properties = new HashMap<>();
    final int status = component.updateProperties(model, properties, path.toFile().getName());
    assertThat(status, is(1));
    assertThat(path.toFile().isFile(), is(false));
  }

  /**
   * {@link PropertiesComponent#deleteProperties(ContentsModel, String)} 実行時に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   *   <li>ファイルが作成されないこと。
   * </ul>
   */
  @Test
  public void pre003() throws Exception {
    final int status = component.deleteProperties(model, path.toFile().getName());
    assertThat(status, is(1));
    assertThat(path.toFile().isFile(), is(false));
  }
}
