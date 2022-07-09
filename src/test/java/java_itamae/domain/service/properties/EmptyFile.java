package java_itamae.domain.service.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java_itamae.domain.common.GetTestEncoding;
import java_itamae.domain.model.contents.ContentsModel;

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
    final ContentsModel attr = new ContentsModel();
    attr.setPath("test.properties");

    final PropertiesService service = new PropertiesServiceImpl(attr);

    try {
      service.getProperty("test");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   * {@link PropertiesService#updateProperty(String, String)} 実行時に {@link FileNotFoundException}
   * が送出されること。
   */
  @Test(expected = FileNotFoundException.class)
  public void pse002() throws Exception {
    final ContentsModel attr = new ContentsModel();
    attr.setPath("test.txt");

    final PropertiesService service = new PropertiesServiceImpl(attr);

    try {
      service.updateProperty("test", "更新テスト");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   * {@link PropertiesService#deleteProperty(String)} 実行時に {@link FileNotFoundException} が送出されること。
   */
  @Test(expected = FileNotFoundException.class)
  public void pse003() throws Exception {
    final ContentsModel attr = new ContentsModel();
    attr.setPath("test.txt");

    final PropertiesService service = new PropertiesServiceImpl(attr);

    try {
      service.deleteProperty("test");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link PropertiesService#createProperty(String, String)} 実行時にプロパティファイルへの書込みができること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void pss001() throws Exception {
    final ContentsModel attr = new ContentsModel();
    attr.setPath("test.properties");

    final PropertiesService service = new PropertiesServiceImpl(attr);
    final boolean status = service.createProperty("test", "登録テスト");
    assertThat(status, is(true));
    assertThat(service.getProperty("test"), is("登録テスト"));
  }

  /**
   *
   *
   * <ul>
   *   <li>文字エンコーディングを指定した場合にプロパティファイルへの書込みができること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void pss002() throws Exception {
    final ContentsModel attr = new ContentsModel();
    attr.setPath("test.properties");
    attr.setEncoding(getTestEncoding.get());

    final PropertiesService service = new PropertiesServiceImpl(attr);
    final boolean status = service.createProperty("test", "登録テスト");
    assertThat(status, is(true));
    assertThat(service.getProperty("test"), is("登録テスト"));
  }
}
