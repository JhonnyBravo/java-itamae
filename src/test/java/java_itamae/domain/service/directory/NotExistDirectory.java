package java_itamae.domain.service.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
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
  private Path path;

  @Before
  public void setUp() throws Exception {
    ds = new DirectoryServiceImpl();
    path = FileSystems.getDefault().getPath("test_dir");

    final ContentsModel model = new ContentsModel();
    model.setPath("src/test/resources/test.properties");
    ps = new PropertiesServiceImpl();
    ps.init(model);
  }

  @After
  public void tearDown() throws Exception {
    if (path.toFile().isDirectory()) {
      Files.delete(path);
    }
  }

  /**
   *
   *
   * <ul>
   *   <li>ディレクトリが作成されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void dss001() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath(path.toFile().getPath());

    final int status = ds.create(model);
    assertThat(status, is(2));
    assertThat(path.toFile().isDirectory(), is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>ディレクトリが作成されること。
   *   <li>所有者が変更されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void dss002() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath(path.toFile().getPath());
    model.setOwner(ps.getProperty("owner"));

    final int status = ds.create(model);
    assertThat(status, is(2));
    assertThat(path.toFile().isDirectory(), is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>ディレクトリが作成されること。
   *   <li>グループ所有者が変更されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void dss003() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath(path.toFile().getPath());
    model.setGroup(ps.getProperty("group"));

    final int status = ds.create(model);
    assertThat(status, is(2));
    assertThat(path.toFile().isDirectory(), is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>ディレクトリが作成されること。
   *   <li>パーミッション設定値が変更されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void dss004() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath(path.toFile().getPath());
    model.setMode("741");

    final int status = ds.create(model);
    assertThat(status, is(2));
    assertThat(path.toFile().isDirectory(), is(true));
  }
}
