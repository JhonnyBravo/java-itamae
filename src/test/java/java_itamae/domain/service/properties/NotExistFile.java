package java_itamae.domain.service.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java_itamae.domain.model.contents.ContentsModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** ファイルが存在しない場合のテスト。 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NotExistFile {
  @Autowired private PropertiesService service;
  private File file;

  @Before
  public void setUp() throws Exception {
    final ContentsModel attr = new ContentsModel();
    attr.setPath("NotExist.txt");

    service.init(attr);
    file = new File(attr.getPath());
  }

  /**
   * プロパティファイルが存在しない場合に {@link PropertiesService#getProperty(String)} を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link FileNotFoundException} が発生すること。
   * </ul>
   */
  @Test(expected = FileNotFoundException.class)
  public void pse001() throws Exception {
    service.getProperty("test");
  }

  /**
   * プロパティファイルが存在しない場合に {@link PropertiesService#createProperty(String, String)} を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   *   <li>ファイルの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link FileNotFoundException} が発生すること。
   *   <li>プロパティファイルが存在しないこと。
   * </ul>
   */
  @Test(expected = FileNotFoundException.class)
  public void pse002() throws Exception {
    try {
      service.createProperty("test", "登録テスト");
    } catch (Exception e) {
      assertThat(file.isFile(), is(false));
      throw e;
    }
  }

  /**
   * ファイルが存在しない場合に {@link PropertiesService#updateProperty(String, String)} を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   *   <li>ファイルの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link FileNotFoundException} が発生すること。
   *   <li>プロパティファイルが存在しないこと。
   * </ul>
   */
  @Test(expected = FileNotFoundException.class)
  public void pse003() throws Exception {
    try {
      service.updateProperty("test", "更新テスト");
    } catch (Exception e) {
      assertThat(file.isFile(), is(false));
      throw e;
    }
  }

  /**
   * ファイルが存在しない場合に {@link PropertiesService#deleteProperty(String)} を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   *   <li>ファイルの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link FileNotFoundException} が発生すること。
   *   <li>プロパティファイルが存在しないこと。
   * </ul>
   */
  @Test(expected = FileNotFoundException.class)
  public void pse004() throws Exception {
    try {
      service.deleteProperty("test");
    } catch (Exception e) {
      assertThat(file.isFile(), is(false));
      throw e;
    }
  }
}
