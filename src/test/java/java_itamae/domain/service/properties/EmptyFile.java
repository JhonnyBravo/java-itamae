package java_itamae.domain.service.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java_itamae.domain.common.GetTestEncoding;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.status.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ファイルが存在して空である場合のテスト */
public class EmptyFile {
  private File file;
  private GetTestEncoding getTestEncoding;

  @Before
  public void setUp() throws Exception {
    getTestEncoding = new GetTestEncoding();
    file = new File("test.properties");
    file.createNewFile();
  }

  @After
  public void tearDown() throws Exception {
    file.delete();
  }

  /**
   * プロパティファイルが空である場合に {@link PropertiesService#createProperty(String, String)} を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link IllegalArgumentException} が発生すること。
   * </ul>
   */
  @Test(expected = IllegalArgumentException.class)
  public void pse001() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    final PropertiesService service = new PropertiesServiceImpl();
    service.init(model);
    service.getProperty("test");
  }

  /**
   * プロパティファイルが空である場合に {@link PropertiesService#updateProperty(String, String)} を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link IllegalArgumentException} が発生すること。
   * </ul>
   */
  @Test(expected = IllegalArgumentException.class)
  public void pse002() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    final PropertiesService service = new PropertiesServiceImpl();
    service.init(model);
    service.updateProperty("test", "更新テスト");
  }

  /**
   * プロパティファイルが空である場合に {@link PropertiesService#deleteProperty(String)} を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link IllegalArgumentException} が発生すること。
   * </ul>
   */
  @Test(expected = IllegalArgumentException.class)
  public void pse003() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    final PropertiesService service = new PropertiesServiceImpl();
    service.init(model);
    service.deleteProperty("test");
  }

  /**
   * プロパティファイルが空である場合に {@link PropertiesService#createProperty(String, String)} を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>返り値の確認。
   *   <li>プロパティファイルの内容確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>プロパティファイルへの書込んだプロパティの値がテスト用データと一致すること。
   * </ul>
   */
  @Test
  public void pss001() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    final PropertiesService service = new PropertiesServiceImpl();
    service.init(model);
    final Status status = service.createProperty("test", "登録テスト");
    assertThat(status, is(Status.DONE));
    assertThat(service.getProperty("test"), is("登録テスト"));
  }

  /**
   * 文字エンコーディングを指定して {@link PropertiesService#createProperty(String, String)} を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>返り値の確認。
   *   <li>プロパティファイルの内容確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>プロパティファイルへの書込んだプロパティの値がテスト用データと一致すること。
   * </ul>
   */
  @Test
  public void pss002() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());
    model.setEncoding(getTestEncoding.get());

    final PropertiesService service = new PropertiesServiceImpl();
    service.init(model);
    final Status status = service.createProperty("test", "登録テスト");
    assertThat(status, is(Status.DONE));
    assertThat(service.getProperty("test"), is("登録テスト"));
  }
}
