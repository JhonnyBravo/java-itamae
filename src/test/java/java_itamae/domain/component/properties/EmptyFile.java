package java_itamae.domain.component.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import jakarta.inject.Inject;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java_itamae.domain.common.GetTestEncoding;
import java_itamae.domain.common.GetTestProperties;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.status.Status;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/** プロパティファイルが空である場合のテスト。 */
public class EmptyFile {
  @Inject private PropertiesComponent component;
  @Inject private GetTestEncoding getTestEncoding;
  @Inject private GetTestProperties getTestProperties;
  private Path path;

  @Rule
  public WeldInitiator weld =
      WeldInitiator.from(
              PropertiesComponentImpl.class, GetTestEncoding.class, GetTestProperties.class)
          .inject(this)
          .build();

  @Before
  public void setUp() throws Exception {
    path = component.convertToPath("test.properties");
    Files.createFile(path);
  }

  @After
  public void tearDown() throws Exception {
    Files.delete(path);
  }

  /**
   * 操作対象とするプロパティファイルの内容が空である場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link PropertiesComponent#getProperties(ContentsModel)} 実行後の返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>空の {@link Map} が返されること。
   * </ul>
   */
  @Test
  public void prs001() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());

    final Map<String, String> properties = component.getProperties(model);
    assertThat(properties.size(), is(0));
  }

  /**
   * 操作対象とするプロパティファイルの内容が空である場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link PropertiesComponent#updateProperties(ContentsModel, Map, String)} 実行後の返り値の確認。
   *   <li>プロパティファイルの内容確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>プロパティファイルへ書き込まれたキーの数がテスト用データと一致すること。
   *   <li>プロパティファイルへ書き込まれたキーと値がテスト用データと一致すること。
   * </ul>
   */
  @Test
  public void prs002() throws Exception {
    final Map<String, String> newProps = getTestProperties.get();

    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());

    final Status status = component.updateProperties(model, newProps, model.getPath());
    assertThat(status, is(Status.DONE));

    final Map<String, String> curProps = component.getProperties(model);
    assertThat(curProps.size(), is(2));
    assertThat(curProps.get("property1"), is(newProps.get("property1")));
    assertThat(curProps.get("property2"), is(newProps.get("property2")));
  }

  /**
   * 文字エンコーディングを指定して {@link PropertiesComponent#updateProperties(ContentsModel, Map, String)}
   * を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link PropertiesComponent#updateProperties(ContentsModel, Map, String)} 実行後の返り値の確認。
   *   <li>プロパティファイルの内容確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>プロパティファイルへ書き込まれたキーの数がテスト用データと一致すること。
   *   <li>プロパティファイルへ書き込まれたキーと値がテスト用データと一致すること。
   * </ul>
   */
  @Test
  public void prs003() throws Exception {
    final Map<String, String> newProps = getTestProperties.get();

    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());
    model.setEncoding(getTestEncoding.get());

    final Status status = component.updateProperties(model, newProps, model.getPath());
    assertThat(status, is(Status.DONE));

    final Map<String, String> curProps = component.getProperties(model);
    assertThat(curProps.size(), is(2));
    assertThat(curProps.get("property1"), is(newProps.get("property1")));
    assertThat(curProps.get("property2"), is(newProps.get("property2")));
  }

  /**
   * 操作対象とするプロパティファイルの内容が空である場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link PropertiesComponent#deleteProperties(ContentsModel, String)} 実行後の返り値の確認。
   *   <li>プロパティファイルの内容確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#INIT} が返されること。
   *   <li>プロパティファイルにキーと値が存在しないこと。
   * </ul>
   */
  @Test
  public void prs004() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());

    final Status status = component.deleteProperties(model, model.getPath());
    assertThat(status, is(Status.INIT));

    final Map<String, String> curProps = component.getProperties(model);
    assertThat(curProps.size(), is(0));
  }
}
