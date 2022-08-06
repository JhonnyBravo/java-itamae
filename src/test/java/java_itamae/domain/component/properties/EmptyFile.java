package java_itamae.domain.component.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java_itamae.domain.common.GetTestEncoding;
import java_itamae.domain.common.GetTestProperties;
import java_itamae.domain.model.contents.ContentsModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** プロパティファイルが空である場合のテスト。 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmptyFile {
  @Autowired private PropertiesComponent component;
  @Autowired private GetTestEncoding getTestEncoding;
  @Autowired private GetTestProperties getTestProperties;
  private Path path;

  @Before
  public void setUp() throws Exception {
    path = component.convertToPath("test.properties");
    Files.createFile(path);
  }

  @After
  public void tearDown() throws Exception {
    Files.delete(path);
  }

  /** {@link PropertiesComponent#getProperties(ContentsModel)} 実行時に空の {@link Map} が返されること。 */
  @Test
  public void prs001() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());

    final Map<String, String> properties = component.getProperties(model);
    assertThat(properties.size(), is(0));
  }

  /**
   * {@link PropertiesComponent#updateProperties(ContentsModel, Map, String)} 実行時に
   *
   * <ul>
   *   <li>ファイルへプロパティの書込みができること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void prs002() throws Exception {
    final Map<String, String> newProps = getTestProperties.get();

    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());

    final int status = component.updateProperties(model, newProps, model.getPath());
    assertThat(status, is(2));

    final Map<String, String> curProps = component.getProperties(model);
    assertThat(curProps.size(), is(2));
    assertThat(curProps.get("property1"), is(newProps.get("property1")));
    assertThat(curProps.get("property2"), is(newProps.get("property2")));
  }

  /**
   * 文字エンコーディングを指定して {@link PropertiesComponent#updateProperties(ContentsModel, Map, String)}
   * を実行した場合に
   *
   * <ul>
   *   <li>ファイルへプロパティの書込みができること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void prs003() throws Exception {
    final Map<String, String> newProps = getTestProperties.get();

    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());
    model.setEncoding(getTestEncoding.get());

    final int status = component.updateProperties(model, newProps, model.getPath());
    assertThat(status, is(2));

    final Map<String, String> curProps = component.getProperties(model);
    assertThat(curProps.size(), is(2));
    assertThat(curProps.get("property1"), is(newProps.get("property1")));
    assertThat(curProps.get("property2"), is(newProps.get("property2")));
  }

  /** {@link PropertiesComponent#deleteProperties(ContentsModel, String)} 実行時に終了ステータスが 0 であること。 */
  @Test
  public void prs004() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());

    final int status = component.deleteProperties(model, model.getPath());
    assertThat(status, is(0));

    final Map<String, String> curProps = component.getProperties(model);
    assertThat(curProps.size(), is(0));
  }
}
