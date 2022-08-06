package java_itamae.domain.service.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.directory.DirectoryResourceModel;
import java_itamae.domain.service.properties.PropertiesService;

/** 例外発生時のテスト。 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ErrorCases {
  @Autowired private DirectoryService ds;
  @Autowired private PropertiesService ps;
  private Path path;

  @Before
  public void setUp() throws Exception {
    path = FileSystems.getDefault().getPath("test_dir");

    final ContentsModel model = new ContentsModel();
    model.setPath("src/test/resources/test.properties");
    ps.init(model);
  }

  @After
  public void tearDown() throws Exception {
    if (path.toFile().isDirectory()) {
      Files.delete(path);
    }
  }

  /**
   * path が指定されないまま {@link DirectoryService#create(DirectoryResourceModel)} を実行した場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void dse001() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setOwner(ps.getProperty("owner"));
    model.setGroup(ps.getProperty("group"));
    model.setMode("640");

    final int status = ds.create(model);
    assertThat(status, is(1));
  }

  /**
   * path が指定されないまま {@link DirectoryService#delete(DirectoryResourceModel)} を実行した場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void dse002() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    final int status = ds.delete(model);
    assertThat(status, is(1));
  }

  /**
   * 新しいディレクトリ所有者のユーザ名として、存在しないユーザの名前を指定した場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void dse003() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath(path.toFile().getPath());
    model.setOwner("NotExist");
    model.setGroup(ps.getProperty("group"));
    model.setMode("640");

    final int status = ds.create(model);
    assertThat(status, is(1));
    assertThat(path.toFile().isDirectory(), is(true));
  }

  /**
   * 新しいグループ所有者のグループ名として、存在しないグループの名前を指定した場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void dse004() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath(path.toFile().getPath());
    model.setOwner(ps.getProperty("owner"));
    model.setGroup("NotExist");
    model.setMode("640");

    final int status = ds.create(model);
    assertThat(status, is(1));
    assertThat(path.toFile().isDirectory(), is(true));
  }

  /**
   * 新しいパーミッション設定値として不正なパーミッション値を指定した場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void dse005() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath(path.toFile().getPath());
    model.setOwner(ps.getProperty("owner"));
    model.setGroup(ps.getProperty("group"));
    model.setMode("a40");

    final int status = ds.create(model);
    assertThat(status, is(1));
    assertThat(path.toFile().isDirectory(), is(true));
  }
}
