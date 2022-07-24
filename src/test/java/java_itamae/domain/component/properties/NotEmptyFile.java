package java_itamae.domain.component.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java_itamae.domain.common.GetTestEncoding;
import java_itamae.domain.common.GetTestProperties;
import java_itamae.domain.model.contents.ContentsModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** プロパティファイルが空ではない場合のテスト */
public class NotEmptyFile {
  private PropertiesComponent component;
  private GetTestEncoding getTestEncoding;
  private Path path;
  private GetTestProperties getTestProperties;

  @Before
  public void setUp() throws Exception {
    component = new PropertiesComponentImpl();
    getTestEncoding = new GetTestEncoding();
    getTestProperties = new GetTestProperties();

    path = component.convertToPath("test.properties");
    Files.createFile(path);

    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());

    component.updateProperties(model, getTestProperties.get(), model.getPath());
  }

  @After
  public void tearDown() throws Exception {
    Files.delete(path);
  }

  /**
   * {@link PropertiesComponent#getProperties(ContentsModel)} 実行時にプロパティファイルの内容が {@link Map}
   * に変換されて返されること。
   */
  @Test
  public void prs001() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());

    final Map<String, String> newProps = getTestProperties.get();
    final Map<String, String> curProps = component.getProperties(model);

    assertThat(curProps.size(), is(2));
    assertThat(curProps.get("property1"), is(newProps.get("property1")));
    assertThat(curProps.get("property2"), is(newProps.get("property2")));
  }

  /**
   * {@link PropertiesComponent#updateProperties(ContentsModel, Map, String)} 実行時に
   *
   * <ul>
   *   <li>プロパティファイルの上書きができること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void prs002() throws Exception {
    final Map<String, String> newProps = new HashMap<>();
    newProps.put("update", "更新テスト");

    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());

    final int status = component.updateProperties(model, newProps, model.getPath());
    assertThat(status, is(2));

    final Map<String, String> curProps = component.getProperties(model);
    assertThat(curProps.size(), is(1));
    assertThat(curProps.get("update"), is(newProps.get("update")));
  }

  /**
   * 文字エンコーディングを指定して {@link PropertiesComponent#updateProperties(ContentsModel, Map, String)}
   * を実行した場合に
   *
   * <ul>
   *   <li>プロパティファイルの上書きができること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void prs003() throws Exception {
    final Map<String, String> newProps = new HashMap<>();
    newProps.put("encoding", "文字コードテスト");

    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());
    model.setEncoding(getTestEncoding.get());

    final int status = component.updateProperties(model, newProps, model.getPath());
    assertThat(status, is(2));

    final Map<String, String> curProps = component.getProperties(model);
    assertThat(curProps.size(), is(1));
    assertThat(curProps.get("encoding"), is(newProps.get("encoding")));
  }

  /**
   * {@link PropertiesComponent#deleteProperties(ContentsModel, String)} 実行時に
   *
   * <ul>
   *   <li>プロパティファイルを空にできること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void prs004() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());

    final int status = component.deleteProperties(model, model.getPath());
    assertThat(status, is(2));

    final Map<String, String> properties = component.getProperties(model);
    assertThat(properties.size(), is(0));
  }
}
