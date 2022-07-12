package java_itamae.domain.service.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.directory.DirectoryResourceModel;
import java_itamae.domain.service.properties.PropertiesService;
import java_itamae.domain.service.properties.PropertiesServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ディレクトリが存在しない場合のテスト。 */
public class NotExistDirectory {
  private DirectoryService ds;
  private PropertiesService ps;
  private File directory;

  @Before
  public void setUp() throws Exception {
    ds = new DirectoryServiceImpl();
    directory = new File("test_dir");

    final ContentsModel attr = new ContentsModel();
    attr.setPath("src/test/resources/test.properties");
    ps = new PropertiesServiceImpl(attr);
  }

  @After
  public void tearDown() throws Exception {
    if (directory.isDirectory()) {
      directory.delete();
    }
  }

  /**
   *
   *
   * <ul>
   *   <li>ディレクトリが作成されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void dss001() throws Exception {
    final DirectoryResourceModel attr = new DirectoryResourceModel();
    attr.setPath("test_dir");

    final boolean status = ds.create(attr);
    assertThat(status, is(true));
    assertThat(directory.isDirectory(), is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>ディレクトリが作成されること。
   *   <li>所有者が変更されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void dss002() throws Exception {
    final DirectoryResourceModel attr = new DirectoryResourceModel();
    attr.setPath("test_dir");
    attr.setOwner(ps.getProperty("owner"));

    final boolean status = ds.create(attr);
    assertThat(status, is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>ディレクトリが作成されること。
   *   <li>グループ所有者が変更されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void dss003() throws Exception {
    final DirectoryResourceModel attr = new DirectoryResourceModel();
    attr.setPath("test_dir");
    attr.setGroup(ps.getProperty("group"));

    final boolean status = ds.create(attr);
    assertThat(status, is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>ディレクトリが作成されること。
   *   <li>パーミッション設定値が変更されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void dss004() throws Exception {
    final DirectoryResourceModel attr = new DirectoryResourceModel();
    attr.setPath("test_dir");
    attr.setMode("741");

    final boolean status = ds.create(attr);
    assertThat(status, is(true));
  }
}
