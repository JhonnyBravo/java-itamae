package java_itamae.domain.service.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java_itamae.domain.component.file.FileComponentImpl;
import java_itamae.domain.component.group.GroupComponentImpl;
import java_itamae.domain.component.mode.ModeComponentImpl;
import java_itamae.domain.component.owner.OwnerComponentImpl;
import java_itamae.domain.component.properties.PropertiesComponentImpl;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.file.FileResourceModel;
import java_itamae.domain.service.properties.PropertiesService;
import java_itamae.domain.service.properties.PropertiesServiceImpl;
import javax.inject.Inject;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/** エラーケースのテスト */
public class ErrorCases {
  @Inject private FileService fs;
  @Inject private PropertiesService ps;
  private Path path;

  @Rule
  public WeldInitiator weld =
      WeldInitiator.from(
              FileServiceImpl.class,
              FileComponentImpl.class,
              OwnerComponentImpl.class,
              GroupComponentImpl.class,
              ModeComponentImpl.class,
              PropertiesServiceImpl.class,
              PropertiesComponentImpl.class)
          .inject(this)
          .build();

  @Before
  public void setUp() throws Exception {
    path = FileSystems.getDefault().getPath("test.txt");

    final ContentsModel model = new ContentsModel();
    model.setPath("src/test/resources/test.properties");

    ps.init(model);
  }

  @After
  public void tearDown() throws Exception {
    if (path.toFile().isFile()) {
      Files.delete(path);
    }
  }

  /**
   * path が指定されないまま {@link FileService#create(FileResourceModel)} を実行した場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void fse001() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setOwner(ps.getProperty("owner"));
    model.setGroup(ps.getProperty("group"));
    model.setMode("640");

    final int status = fs.create(model);
    assertThat(status, is(1));
  }

  /**
   * path が指定されないまま {@link FileService#delete(FileResourceModel)} を実行した場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void fse002() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    final int status = fs.delete(model);
    assertThat(status, is(1));
  }

  /**
   * 親ディレクトリが存在しない場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void fse003() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath("NotExist/test.txt");
    model.setOwner(ps.getProperty("owner"));
    model.setGroup(ps.getProperty("group"));
    model.setMode("640");

    final int status = fs.create(model);
    assertThat(status, is(1));
  }

  /**
   * 新しいファイル所有者として、存在しないユーザの名前を指定した場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void fse004() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath(path.toFile().getPath());
    model.setOwner("NotExist");
    model.setGroup(ps.getProperty("group"));
    model.setMode("640");

    final int status = fs.create(model);
    assertThat(status, is(1));
  }

  /**
   * 新しいグループ所有者として、存在しないグループの名前を指定した場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void fse005() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath(path.toFile().getPath());
    model.setOwner(ps.getProperty("owner"));
    model.setGroup("NotExist");
    model.setMode("640");

    final int status = fs.create(model);
    assertThat(status, is(1));
  }

  /**
   * 新しいパーミッション設定として不正なパーミッション設定値を指定した場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void fse006() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath(path.toFile().getPath());
    model.setOwner(ps.getProperty("owner"));
    model.setGroup(ps.getProperty("group"));
    model.setMode("a40");

    final int status = fs.create(model);
    assertThat(status, is(1));
  }
}
