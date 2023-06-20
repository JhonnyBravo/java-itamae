package java_itamae.domain.service.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import jakarta.inject.Inject;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java_itamae.domain.component.directory.DirectoryComponentImpl;
import java_itamae.domain.component.group.GroupComponentImpl;
import java_itamae.domain.component.mode.ModeComponentImpl;
import java_itamae.domain.component.owner.OwnerComponentImpl;
import java_itamae.domain.component.properties.PropertiesComponentImpl;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.directory.DirectoryResourceModel;
import java_itamae.domain.model.status.Status;
import java_itamae.domain.service.properties.PropertiesService;
import java_itamae.domain.service.properties.PropertiesServiceImpl;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/** ディレクトリが存在する場合のテスト。 */
public class ExistDirectory2 {
  @Inject private DirectoryService ds;
  @Inject private PropertiesService ps;
  private Path path;

  @Rule
  public WeldInitiator weld =
      WeldInitiator.from(
              DirectoryServiceImpl.class,
              DirectoryComponentImpl.class,
              OwnerComponentImpl.class,
              GroupComponentImpl.class,
              ModeComponentImpl.class,
              PropertiesServiceImpl.class,
              PropertiesComponentImpl.class)
          .inject(this)
          .build();

  @Before
  public void setUp() throws Exception {
    path = FileSystems.getDefault().getPath("test_dir");
    Files.createDirectory(path);

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
   * 操作対象とするディレクトリが存在しない場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link DirectoryService#create(DirectoryResourceModel)} 実行後の返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   * </ul>
   */
  @Test
  public void dss001() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath(path.toFile().getPath());
    model.setOwner(ps.getProperty("owner"));

    final Status status = ds.create(model);
    assertThat(status, is(Status.DONE));
  }

  /**
   * 新しいディレクトリ所有者のユーザ名と、現在設定されているディレクトリ所有者のユーザ名が異なる場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link DirectoryService#create(DirectoryResourceModel)} 実行後の返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   * </ul>
   */
  @Test
  public void dss002() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath(path.toFile().getPath());
    model.setOwner(ps.getProperty("owner"));

    final Status status = ds.create(model);
    assertThat(status, is(Status.DONE));
  }

  /**
   * ディレクトリの新しいグループ所有者のグループ名と、現在設定されているグループ所有者のグループ名が異なる場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link DirectoryService#create(DirectoryResourceModel)} 実行後の返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void dss003() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath(path.toFile().getPath());
    model.setGroup(ps.getProperty("group"));

    final Status status = ds.create(model);
    assertThat(status, is(Status.DONE));
  }

  /**
   * 新しいパーミッション設定値と、現在設定されているパーミッション設定値が異なる場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link DirectoryService#create(DirectoryResourceModel)} 実行後の返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void dss004() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath(path.toFile().getPath());
    model.setMode("741");

    final Status status = ds.create(model);
    assertThat(status, is(Status.DONE));
  }
}
