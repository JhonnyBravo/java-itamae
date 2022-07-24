package java_itamae.domain.service.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java_itamae.domain.common.IsWindows;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.directory.DirectoryResourceModel;
import java_itamae.domain.service.properties.PropertiesService;
import java_itamae.domain.service.properties.PropertiesServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ディレクトリが存在する場合のテスト。 */
public class ExistDirectory1 {
  private IsWindows isWindows;
  private DirectoryService ds;
  private PropertiesService ps;
  private Path path;

  @Before
  public void setUp() throws Exception {
    isWindows = new IsWindows();
    final ContentsModel cm = new ContentsModel();
    cm.setPath("src/test/resources/test.properties");
    ps = new PropertiesServiceImpl();
    ps.init(cm);

    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath("test_dir");
    model.setOwner(ps.getProperty("owner"));

    if (!isWindows.get()) {
      model.setGroup(ps.getProperty("group"));
      model.setMode("640");
    }

    ds = new DirectoryServiceImpl();
    ds.create(model);

    path = FileSystems.getDefault().getPath(model.getPath());
  }

  @After
  public void tearDown() throws Exception {
    if (path.toFile().isDirectory()) {
      Files.delete(path);
    }
  }

  /**
   * path に指定したディレクトリが既に存在する場合に
   *
   * <ul>
   *   <li>操作が実行されないこと。
   *   <li>終了ステータスが 0 であること。
   * </ul>
   */
  @Test
  public void dss001() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath(path.toFile().getPath());

    final int status = ds.create(model);
    assertThat(status, is(0));
  }

  /**
   * 新しいディレクトリ所有者のユーザ名と、現在設定されているディレクトリ所有者のユーザ名が同一である場合に
   *
   * <ul>
   *   <li>所有者が変更されないこと。
   *   <li>終了ステータスが 0 であること。
   * </ul>
   */
  @Test
  public void dss002() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath(path.toFile().getPath());
    model.setOwner(ps.getProperty("owner"));

    final int status = ds.create(model);
    assertThat(status, is(0));
  }

  /**
   * 新しいグループ所有者のグループ名と、現在設定されているグループ所有者のグループ名が同一である場合に
   *
   * <ul>
   *   <li>グループ所有者が変更されないこと。
   *   <li>終了ステータスが 0 であること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void dss003() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath(path.toFile().getPath());
    model.setGroup(ps.getProperty("group"));

    final int status = ds.create(model);
    assertThat(status, is(0));
  }

  /**
   * 新しいパーミッション設定値と、現在設定されているパーミッション設定値が同一である場合に
   *
   * <ul>
   *   <li>パーミッション設定値が変更されないこと。
   *   <li>終了ステータスが 0 であること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void dss004() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath(path.toFile().getPath());
    model.setMode("640");

    final int status = ds.create(model);
    assertThat(status, is(0));
  }
}
