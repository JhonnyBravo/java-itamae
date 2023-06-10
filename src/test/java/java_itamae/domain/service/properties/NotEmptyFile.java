package java_itamae.domain.service.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.status.Status;
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

    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    final PropertiesService service = new PropertiesServiceImpl();
    service.init(model);
    service.createProperty("property1", "1 つ目のプロパティ");
    service.createProperty("property2", "2 つ目のプロパティ");
  }

  @After
  public void tearDown() throws Exception {
    file.delete();
  }

  /**
   * すでに存在するプロパティーキーを対象に {@link PropertiesService#createProperty(String, String)} を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   *   <li>プロパティファイルの内容確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link IllegalArgumentException} が発生すること。
   *   <li>プロパティの値がテスト用データと一致すること。
   * </ul>
   */
  @Test(expected = IllegalArgumentException.class)
  public void pse001() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    final PropertiesService service = new PropertiesServiceImpl();
    service.init(model);

    try {
      service.createProperty("property1", "登録テスト");
    } catch (Exception e) {
      assertThat(service.getProperty("property1"), is("1 つ目のプロパティ"));
      throw e;
    }
  }

  /**
   * {@link PropertiesService#getProperty(String)} の動作検証を実施する。
   *
   * <ul>
   *   <li>返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>取得したプロパティの値がテスト用データと一致すること。
   * </ul>
   */
  @Test
  public void pss001() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    final PropertiesService service = new PropertiesServiceImpl();
    service.init(model);
    assertThat(service.getProperty("property1"), is("1 つ目のプロパティ"));
    assertThat(service.getProperty("property2"), is("2 つ目のプロパティ"));
  }

  /**
   * {@link PropertiesService#updateProperty(String, String)} の動作検証を実施する。
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
   *   <li>上書きしたプロパティの値がテスト用データと一致すること。
   * </ul>
   */
  @Test
  public void pss002() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    final PropertiesService service = new PropertiesServiceImpl();
    service.init(model);

    final Status status = service.updateProperty("property2", "更新テスト");
    assertThat(status, is(Status.DONE));
    assertThat(service.getProperty("property2"), is("更新テスト"));
    assertThat(service.getProperty("property1"), is("1 つ目のプロパティ"));
  }

  /**
   * {@link PropertiesService#updateProperty(String, String)} 実行時にプロパティの値が変更前と同一である場合の動作検証を実施する。
   *
   * <ul>
   *   <li>返り値の確認。
   *   <li>プロパティファイルの内容確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#INIT} を返すこと。
   *   <li>プロパティの値がテスト用データと一致すること。
   * </ul>
   */
  @Test
  public void pss003() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    final PropertiesService service = new PropertiesServiceImpl();
    service.init(model);
    final Status status = service.updateProperty("property1", "1 つ目のプロパティ");
    assertThat(status, is(Status.INIT));
    assertThat(service.getProperty("property1"), is("1 つ目のプロパティ"));
    assertThat(service.getProperty("property2"), is("2 つ目のプロパティ"));
  }

  /**
   * {@link PropertiesService#deleteProperty(String)} の動作検証を実施する。
   *
   * <ul>
   *   <li>返り値の確認。
   *   <li>プロパティファイルの内容確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} を返すこと。
   *   <li>指定したプロパティが削除されていること。
   * </ul>
   */
  @Test(expected = IllegalArgumentException.class)
  public void pss004() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    final PropertiesService service = new PropertiesServiceImpl();
    service.init(model);
    final Status status = service.deleteProperty("property1");
    assertThat(status, is(Status.DONE));
    assertThat(service.getProperty("property2"), is("2 つ目のプロパティ"));

    service.getProperty("property1");
  }
}
