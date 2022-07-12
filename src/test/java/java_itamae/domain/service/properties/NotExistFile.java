package java_itamae.domain.service.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java_itamae.domain.model.contents.ContentsModel;
import org.junit.Before;
import org.junit.Test;

/** ファイルが存在しない場合のテスト。 */
public class NotExistFile {
  private PropertiesService service;
  private File file;

  @Before
  public void setUp() throws Exception {
    final ContentsModel attr = new ContentsModel();
    attr.setPath("NotExist.txt");

    service = new PropertiesServiceImpl(attr);
    file = new File(attr.getPath());
  }

  /** {@link PropertiesService#getProperty(String)} 実行時に {@link FileNotFoundException} が送出されること。 */
  @Test(expected = FileNotFoundException.class)
  public void pse001() throws Exception {
    try {
      service.getProperty("test");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   * {@link PropertiesService#createProperty(String, String)} 実行時に {@link FileNotFoundException}
   * が送出されること。
   */
  @Test(expected = FileNotFoundException.class)
  public void pse002() throws Exception {
    try {
      service.createProperty("test", "登録テスト");
    } catch (final Exception e) {
      assertThat(file.isFile(), is(false));
      System.err.println(e);
      throw e;
    }
  }

  /**
   * {@link PropertiesService#updateProperty(String, String)} 実行時に {@link FileNotFoundException}
   * が送出されること。
   */
  @Test(expected = FileNotFoundException.class)
  public void pse003() throws Exception {
    try {
      service.updateProperty("test", "更新テスト");
    } catch (final Exception e) {
      assertThat(file.isFile(), is(false));
      System.err.println(e);
      throw e;
    }
  }

  /**
   * {@link PropertiesService#deleteProperty(String)} 実行時に {@link FileNotFoundException} が送出されること。
   */
  @Test(expected = FileNotFoundException.class)
  public void pse004() throws Exception {
    try {
      service.deleteProperty("test");
    } catch (final Exception e) {
      assertThat(file.isFile(), is(false));
      System.err.println(e);
      throw e;
    }
  }
}
