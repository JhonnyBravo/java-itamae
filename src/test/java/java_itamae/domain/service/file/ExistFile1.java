package java_itamae.domain.service.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java_itamae.domain.common.IsWindows;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.file.FileResourceModel;
import java_itamae.domain.model.status.Status;
import java_itamae.domain.service.properties.PropertiesService;
import java_itamae.domain.service.properties.PropertiesServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ファイルが既に存在する場合のテスト */
public class ExistFile1 {
  private FileService fs;
  private PropertiesService ps;
  private IsWindows isWindows;
  private Path path;

  @Before
  public void setUp() throws Exception {
    isWindows = new IsWindows();
    path = FileSystems.getDefault().getPath("test.txt");

    final ContentsModel cm = new ContentsModel();
    cm.setPath("src/test/resources/test.properties");
    ps = new PropertiesServiceImpl();
    ps.init(cm);

    final FileResourceModel frm = new FileResourceModel();
    frm.setPath(path.toFile().getPath());
    frm.setOwner(ps.getProperty("owner"));

    if (!isWindows.get()) {
      frm.setGroup(ps.getProperty("group"));
      frm.setMode("640");
    }

    fs = new FileServiceImpl();
    fs.create(frm);
  }

  @After
  public void tearDown() throws Exception {
    if (path.toFile().isFile()) {
      Files.delete(path);
    }
  }

  /**
   * 操作対象とするファイルが既に存在する場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link FileService#create(FileResourceModel)} 実行後の返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#INIT} が返されること。
   * </ul>
   */
  @Test
  public void fss001() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath(path.toFile().getPath());

    final Status status = fs.create(model);
    assertThat(status, is(Status.INIT));
  }

  /**
   * 新しいファイル所有者のユーザ名と、現在ファイルに設定されている所有者のユーザ名が同一である場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link FileService#create(FileResourceModel)} 実行後の返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#INIT} が返されること。
   * </ul>
   */
  @Test
  public void fss002() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath(path.toFile().getPath());
    model.setOwner(ps.getProperty("owner"));

    final Status status = fs.create(model);
    assertThat(status, is(Status.INIT));
  }

  /**
   * 新しいグループ所有者のグループ名と、現在ファイルに設定されているグループ所有者のグループ名が同一である場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link FileService#create(FileResourceModel)} 実行後の返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#INIT} が返されること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない")
  @Test
  public void fss003() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath(path.toFile().getPath());
    model.setGroup(ps.getProperty("group"));

    final Status status = fs.create(model);
    assertThat(status, is(Status.INIT));
  }

  /**
   * 新しいパーミッション設定値と、現在ファイルに設定されているパーミッション設定値が同一である場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link FileService#create(FileResourceModel)} 実行後の返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#INIT} が返されること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない")
  @Test
  public void fss004() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath(path.toFile().getPath());
    model.setMode("640");

    final Status status = fs.create(model);
    assertThat(status, is(Status.INIT));
  }

  /**
   * 以下の検証を実施する。
   *
   * <ul>
   *   <li>{@link FileService#delete(FileResourceModel)} 実行後の返り値の確認。
   *   <li>ファイルの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>ファイルが存在しないこと。
   * </ul>
   */
  @Test
  public void fss005() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath(path.toFile().getPath());

    final Status status = fs.delete(model);
    assertThat(status, is(Status.DONE));
    assertThat(path.toFile().isFile(), is(false));
  }
}
