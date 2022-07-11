package java_itamae.domain.service.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java_itamae.domain.common.IsWindows;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.directory.DirectoryResourceModel;
import java_itamae.domain.service.properties.PropertiesService;
import java_itamae.domain.service.properties.PropertiesServiceImpl;

/** ディレクトリが存在する場合のテスト。 */
public class ExistDirectory1 {
  private IsWindows isWindows;
  private DirectoryService ds;
  private PropertiesService ps;
  private File file;

  @Before
  public void setUp() throws Exception {
    isWindows = new IsWindows();
    final ContentsModel ca = new ContentsModel();
    ca.setPath("src/test/resources/test.properties");
    ps = new PropertiesServiceImpl(ca);

    final DirectoryResourceModel attr = new DirectoryResourceModel();
    attr.setPath("test_dir");
    attr.setOwner(ps.getProperty("owner"));

    if (!isWindows.get()) {
      attr.setGroup(ps.getProperty("group"));
      attr.setMode("640");
    }

    ds = new DirectoryServiceImpl();
    ds.create(attr);

    file = new File("test_dir");
  }

  @After
  public void tearDown() throws Exception {
    if (file.isDirectory()) {
      file.delete();
    }
  }

  /** path に指定したディレクトリが既に存在する場合に終了ステータスが false であること。 */
  @Test
  public void dss001() throws Exception {
    final DirectoryResourceModel attr = new DirectoryResourceModel();
    attr.setPath("test_dir");

    final boolean status = ds.create(attr);
    assertThat(status, is(false));
  }

  /** 新しいディレクトリ所有者のユーザ名と、現在設定されているディレクトリ所有者のユーザ名が同一である場合に終了ステータスが false であること。 */
  @Test
  public void dss002() throws Exception {
    final DirectoryResourceModel attr = new DirectoryResourceModel();
    attr.setPath("test_dir");
    attr.setOwner(ps.getProperty("owner"));

    final boolean status = ds.create(attr);
    assertThat(status, is(false));
  }

  /** 新しいグループ所有者のグループ名と、現在設定されているグループ所有者のグループ名が同一である場合に終了ステータスが false であること。 */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void dss003() throws Exception {
    final DirectoryResourceModel attr = new DirectoryResourceModel();
    attr.setPath("test_dir");
    attr.setGroup(ps.getProperty("group"));

    final boolean status = ds.create(attr);
    assertThat(status, is(false));
  }

  /** 新しいパーミッション設定値と、現在設定されているパーミッション設定値が同一である場合に終了ステータスが false であること。 */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void dss004() throws Exception {
    final DirectoryResourceModel attr = new DirectoryResourceModel();
    attr.setPath("test_dir");
    attr.setMode("640");

    final boolean status = ds.create(attr);
    assertThat(status, is(false));
  }
}
