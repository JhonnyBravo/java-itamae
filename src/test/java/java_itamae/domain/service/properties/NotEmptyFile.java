package java_itamae.domain.service.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import jakarta.inject.Inject;
import java.io.File;
import java_itamae.domain.component.properties.PropertiesComponentImpl;
import java_itamae.domain.model.contents.ContentsModel;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/** ファイルが存在して内容が空ではない場合のテスト */
public class NotEmptyFile {
  private File file;
  @Inject private PropertiesService service;

  @Rule
  public WeldInitiator weld =
      WeldInitiator.from(PropertiesServiceImpl.class, PropertiesComponentImpl.class)
          .inject(this)
          .build();

  @Before
  public void setUp() throws Exception {
    file = new File("test.txt");
    file.createNewFile();

    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    service.init(model);
    service.createProperty("property1", "1 つ目のプロパティ");
    service.createProperty("property2", "2 つ目のプロパティ");
  }

  @After
  public void tearDown() throws Exception {
    file.delete();
  }

  /**
   * すでに存在するプロパティーキーを対象に {@link PropertiesService#createProperty(String, String)} を実行した場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void pse001() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    service.init(model);

    final int status = service.createProperty("property1", "登録テスト");
    assertThat(status, is(1));
    assertThat(service.getProperty("property1"), is("1 つ目のプロパティ"));
  }

  /** {@link PropertiesService#getProperty(String)} 実行時に引数に指定したキーの値を取得できること。 */
  @Test
  public void pss001() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    service.init(model);
    assertThat(service.getProperty("property1"), is("1 つ目のプロパティ"));
    assertThat(service.getProperty("property2"), is("2 つ目のプロパティ"));
  }

  /**
   * {@link PropertiesService#updateProperty(String, String)} 実行時に
   *
   * <ul>
   *   <li>プロパティの値を上書きできること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void pss002() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    service.init(model);

    final int status = service.updateProperty("property2", "更新テスト");
    assertThat(status, is(2));
    assertThat(service.getProperty("property2"), is("更新テスト"));
    assertThat(service.getProperty("property1"), is("1 つ目のプロパティ"));
  }

  /**
   * {@link PropertiesService#updateProperty(String, String)} 実行時にプロパティの値が変更前と同一である場合に終了ステータスが 0
   * であること。
   */
  @Test
  public void pss003() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    service.init(model);
    final int status = service.updateProperty("property1", "1 つ目のプロパティ");
    assertThat(status, is(0));
    assertThat(service.getProperty("property1"), is("1 つ目のプロパティ"));
    assertThat(service.getProperty("property2"), is("2 つ目のプロパティ"));
  }

  /**
   * {@link PropertiesService#deleteProperty(String)} 実行時に
   *
   * <ul>
   *   <li>指定したプロパティを削除できること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void pss004() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    service.init(model);
    final int status = service.deleteProperty("property1");
    assertThat(status, is(2));
    assertThat(service.getProperty("property2"), is("2 つ目のプロパティ"));

    try {
      service.getProperty("property1");
    } catch (final Exception e) {
      System.err.println(e);
    }
  }
}
