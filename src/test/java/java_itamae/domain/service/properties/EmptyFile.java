package java_itamae.domain.service.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java_itamae.domain.common.GetTestEncoding;
import java_itamae.domain.model.contents.ContentsModel;
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

  /** {@link PropertiesService#getProperty(String)} 実行時に {@link Exception} が送出されること。 */
  @Test(expected = Exception.class)
  public void pse001() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    final PropertiesService service = new PropertiesServiceImpl();
    service.init(model);

    try {
      service.getProperty("test");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   * {@link PropertiesService#updateProperty(String, String)} 実行時に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void pse002() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    final PropertiesService service = new PropertiesServiceImpl();
    service.init(model);

    final int status = service.updateProperty("test", "更新テスト");
    assertThat(status, is(1));
  }

  /**
   * {@link PropertiesService#deleteProperty(String)} 実行時に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void pse003() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    final PropertiesService service = new PropertiesServiceImpl();
    service.init(model);

    final int status = service.deleteProperty("test");
    assertThat(status, is(1));
  }

  /**
   * {@link PropertiesService#createProperty(String, String)} 実行時に
   *
   * <ul>
   *   <li>プロパティファイルへの書込みができること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void pss001() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    final PropertiesService service = new PropertiesServiceImpl();
    service.init(model);
    final int status = service.createProperty("test", "登録テスト");
    assertThat(status, is(2));
    assertThat(service.getProperty("test"), is("登録テスト"));
  }

  /**
   * 文字エンコーディングを指定して {@link PropertiesService#createProperty(String, String)} を実行した場合に
   *
   * <ul>
   *   <li>プロパティファイルへの書込みができること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void pss002() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());
    model.setEncoding(getTestEncoding.get());

    final PropertiesService service = new PropertiesServiceImpl();
    service.init(model);
    final int status = service.createProperty("test", "登録テスト");
    assertThat(status, is(2));
    assertThat(service.getProperty("test"), is("登録テスト"));
  }
}
