package java_itamae.domain.service.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import jakarta.inject.Inject;
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
import java_itamae.domain.model.status.Status;
import java_itamae.domain.service.properties.PropertiesService;
import java_itamae.domain.service.properties.PropertiesServiceImpl;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/** ファイルが存在しない場合のテスト */
public class NotExistFile {
  @Inject private FileService fs;
  @Inject private PropertiesService ps;
  private FileResourceModel model;
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
    final ContentsModel ca = new ContentsModel();
    ca.setPath("src/test/resources/test.properties");

    ps.init(ca);

    model = new FileResourceModel();
    model.setPath("test.txt");

    path = FileSystems.getDefault().getPath("test.txt");
  }

  @After
  public void tearDown() throws Exception {
    if (path.toFile().isFile()) {
      Files.delete(path);
    }
  }

  /**
   * ファイル作成の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link FileService#create(FileResourceModel)} 実行後の返り値の確認。
   *   <li>ファイルの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>ファイルが存在すること。
   * </ul>
   */
  @Test
  public void fss001() throws Exception {
    final Status status = fs.create(model);
    assertThat(status, is(Status.DONE));
    assertThat(path.toFile().isFile(), is(true));
  }

  /**
   * ファイル所有者変更の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link FileService#create(FileResourceModel)} 実行後の返り値の確認。
   *   <li>ファイルの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>ファイルが存在すること。
   * </ul>
   */
  @Test
  public void fss002() throws Exception {
    model.setOwner(ps.getProperty("owner"));
    final Status status = fs.create(model);
    assertThat(status, is(Status.DONE));
    assertThat(path.toFile().isFile(), is(true));
  }

  /**
   * ファイルのグループ所有者変更の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link FileService#create(FileResourceModel)} 実行後の返り値の確認。
   *   <li>ファイルの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>ファイルが存在すること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void fss003() throws Exception {
    model.setGroup(ps.getProperty("group"));
    final Status status = fs.create(model);
    assertThat(status, is(Status.DONE));
    assertThat(path.toFile().isFile(), is(true));
  }

  /**
   * ファイルパーミッション変更の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link FileService#create(FileResourceModel)} 実行後の返り値の確認。
   *   <li>ファイルの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>ファイルが存在すること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void fss004() throws Exception {
    model.setMode("721");
    final Status status = fs.create(model);
    assertThat(status, is(Status.DONE));
    assertThat(path.toFile().isFile(), is(true));
  }

  /**
   * ファイル削除の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link FileService#delete(FileResourceModel)} 実行後の返り値の確認。
   *   <li>ファイルの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#INIT} が返されること。
   *   <li>ファイルが存在しないこと。
   * </ul>
   */
  @Test
  public void fss005() throws Exception {
    final Status status = fs.delete(model);
    assertThat(status, is(Status.INIT));
    assertThat(path.toFile().isFile(), is(false));
  }
}
