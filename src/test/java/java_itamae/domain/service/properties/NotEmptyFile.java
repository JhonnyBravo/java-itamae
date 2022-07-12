package java_itamae.domain.service.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java_itamae.domain.model.contents.ContentsModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ファイルが存在して内容が空ではない場合のテスト */
public class NotEmptyFile {
  private File file;

  @Before
  public void setUp() throws Exception {
    file = new File("test.txt");
    file.createNewFile();

    final ContentsModel attr = new ContentsModel();
    attr.setPath("test.txt");

    final PropertiesService service = new PropertiesServiceImpl(attr);
    service.createProperty("property1", "1 つ目のプロパティ");
    service.createProperty("property2", "2 つ目のプロパティ");
  }

  @After
  public void tearDown() throws Exception {
    file.delete();
  }

  /**
   * すでに存在するプロパティーキーを対象に {@link PropertiesService#createProperty(String, String)} を実行した場合に {@link
   * Exception} が送出されること。
   */
  @Test(expected = Exception.class)
  public void pse001() throws Exception {
    final ContentsModel attr = new ContentsModel();
    attr.setPath("test.txt");

    final PropertiesService service = new PropertiesServiceImpl(attr);

    try {
      service.createProperty("property1", "登録テスト");
    } catch (final Exception e) {
      assertThat(service.getProperty("property1"), is("1 つ目のプロパティ"));
      System.err.println(e);
      throw e;
    }
  }

  /** {@link PropertiesService#getProperty(String)} 実行時に引数に指定したキーの値を取得できること。 */
  @Test
  public void pss001() throws Exception {
    final ContentsModel attr = new ContentsModel();
    attr.setPath("test.txt");

    final PropertiesService service = new PropertiesServiceImpl(attr);
    assertThat(service.getProperty("property1"), is("1 つ目のプロパティ"));
    assertThat(service.getProperty("property2"), is("2 つ目のプロパティ"));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link PropertiesService#updateProperty(String, String)} 実行時にプロパティの値を上書きできること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void pss002() throws Exception {
    final ContentsModel attr = new ContentsModel();
    attr.setPath("test.txt");

    final PropertiesService service = new PropertiesServiceImpl(attr);
    final boolean status = service.updateProperty("property2", "更新テスト");
    assertThat(status, is(true));
    assertThat(service.getProperty("property2"), is("更新テスト"));
    assertThat(service.getProperty("property1"), is("1 つ目のプロパティ"));
  }

  /**
   * {@link PropertiesService#updateProperty(String, String)} 実行時にプロパティの値が変更前と同一である場合に終了ステータスが false
   * であること。
   */
  @Test
  public void pss003() throws Exception {
    final ContentsModel attr = new ContentsModel();
    attr.setPath("test.txt");

    final PropertiesService service = new PropertiesServiceImpl(attr);
    final boolean status = service.updateProperty("property1", "1 つ目のプロパティ");
    assertThat(status, is(false));
    assertThat(service.getProperty("property1"), is("1 つ目のプロパティ"));
    assertThat(service.getProperty("property2"), is("2 つ目のプロパティ"));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link PropertiesService#deleteProperty(String)} 実行時に指定したプロパティを削除できること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void pss004() throws Exception {
    final ContentsModel attr = new ContentsModel();
    attr.setPath("test.txt");

    final PropertiesService service = new PropertiesServiceImpl(attr);
    final boolean status = service.deleteProperty("property1");
    assertThat(status, is(true));
    assertThat(service.getProperty("property2"), is("2 つ目のプロパティ"));

    try {
      service.getProperty("property1");
    } catch (final Exception e) {
      System.err.println(e);
    }
  }
}
