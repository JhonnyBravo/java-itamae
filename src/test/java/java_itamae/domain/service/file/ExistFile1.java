package java_itamae.domain.service.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java_itamae.domain.common.IsWindows;
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

/** ファイルが既に存在する場合のテスト */
public class ExistFile1 {
  @Inject private FileService fs;
  @Inject private PropertiesService ps;
  @Inject private IsWindows isWindows;
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
              PropertiesComponentImpl.class,
              IsWindows.class)
          .inject(this)
          .build();

  @Before
  public void setUp() throws Exception {
    path = FileSystems.getDefault().getPath("test.txt");

    final ContentsModel cm = new ContentsModel();
    cm.setPath("src/test/resources/test.properties");

    ps.init(cm);

    final FileResourceModel frm = new FileResourceModel();
    frm.setPath(path.toFile().getPath());
    frm.setOwner(ps.getProperty("owner"));

    if (!isWindows.get()) {
      frm.setGroup(ps.getProperty("group"));
      frm.setMode("640");
    }

    fs.create(frm);
  }

  @After
  public void tearDown() throws Exception {
    if (path.toFile().isFile()) {
      Files.delete(path);
    }
  }

  /**
   * 操作対象とするファイルが既に存在する場合に
   *
   * <ul>
   *   <li>操作が実行されないこと。
   *   <li>終了ステータスが 0 であること。
   * </ul>
   */
  @Test
  public void fss001() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath(path.toFile().getPath());

    final int status = fs.create(model);
    assertThat(status, is(0));
  }

  /**
   * 新しいファイル所有者のユーザ名と、現在ファイルに設定されている所有者のユーザ名が同一である場合に
   *
   * <ul>
   *   <li>ファイル所有者が変更されないこと。
   *   <li>終了ステータスが 0 であること。
   * </ul>
   */
  @Test
  public void fss002() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath(path.toFile().getPath());
    model.setOwner(ps.getProperty("owner"));

    final int status = fs.create(model);
    assertThat(status, is(0));
  }

  /**
   * 新しいグループ所有者のグループ名と、現在ファイルに設定されているグループ所有者のグループ名が同一である場合に
   *
   * <ul>
   *   <li>グループ所有者が変更されないこと。
   *   <li>終了ステータスが 0 であること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない")
  @Test
  public void fss003() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath(path.toFile().getPath());
    model.setGroup(ps.getProperty("group"));

    final int status = fs.create(model);
    assertThat(status, is(0));
  }

  /**
   * 新しいパーミッション設定値と、現在ファイルに設定されているパーミッション設定値が同一である場合に
   *
   * <ul>
   *   <li>パーミッション設定値が変更されないこと。
   *   <li>終了ステータスが 0 であること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない")
  @Test
  public void fss004() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath(path.toFile().getPath());
    model.setMode("640");

    final int status = fs.create(model);
    assertThat(status, is(0));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link FileService#delete(FileResourceModel)} 実行時にファイルが削除されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void fss005() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath(path.toFile().getPath());

    final int status = fs.delete(model);
    assertThat(status, is(2));
    assertThat(path.toFile().isFile(), is(false));
  }
}
