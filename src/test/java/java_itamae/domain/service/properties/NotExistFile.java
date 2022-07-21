package java_itamae.domain.service.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import java_itamae.domain.model.contents.ContentsModel;

/** ファイルが存在しない場合のテスト。 */
public class NotExistFile {
  private PropertiesService service;
  private File file;

  @Before
  public void setUp() throws Exception {
    final ContentsModel attr = new ContentsModel();
    attr.setPath("NotExist.txt");

    service = new PropertiesServiceImpl();
    service.init(attr);
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
   * {@link PropertiesService#createProperty(String, String)} 実行時に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void pse002() throws Exception {
    final int status = service.createProperty("test", "登録テスト");
    assertThat(status, is(1));
    assertThat(file.isFile(), is(false));
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
  public void pse003() throws Exception {
    final int status = service.updateProperty("test", "更新テスト");
    assertThat(status, is(1));
    assertThat(file.isFile(), is(false));
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
  public void pse004() throws Exception {
    final int status = service.deleteProperty("test");
    assertThat(status, is(1));
    assertThat(file.isFile(), is(false));
  }
}
