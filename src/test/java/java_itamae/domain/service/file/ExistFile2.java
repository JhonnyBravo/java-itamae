package java_itamae.domain.service.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.file.FileResourceModel;
import java_itamae.domain.model.status.Status;
import java_itamae.domain.service.properties.PropertiesService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** ファイルが既に存在する場合のテスト */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExistFile2 {
  @Autowired private FileService fs;
  @Autowired private PropertiesService ps;
  private Path path;

  @Before
  public void setUp() throws Exception {
    path = FileSystems.getDefault().getPath("test.txt");
    final ContentsModel cm = new ContentsModel();
    cm.setPath("src/test/resources/test.properties");
    ps.init(cm);

    final FileResourceModel frm = new FileResourceModel();
    frm.setPath("test.txt");

    fs.create(frm);
  }

  @After
  public void tearDown() throws Exception {
    if (path.toFile().isFile()) {
      Files.delete(path);
    }
  }

  /**
   * ファイル所有者変更の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link FileService#create(FileResourceModel)} 実行後の返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   * </ul>
   */
  @Test
  public void fss001() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath(path.toFile().getPath());
    model.setOwner(ps.getProperty("owner"));

    final Status status = fs.create(model);
    assertThat(status, is(Status.DONE));
  }

  /**
   * ファイルのグループ所有者変更の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link FileService#create(FileResourceModel)} 実行後の返り値の確認。
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
  public void fss002() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath(path.toFile().getPath());
    model.setGroup(ps.getProperty("group"));

    final Status status = fs.create(model);
    assertThat(status, is(Status.DONE));
  }

  /**
   * ファイルパーミッション変更の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link FileService#create(FileResourceModel)} 実行後の返り値の確認。
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
  public void fss003() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath(path.toFile().getPath());
    model.setMode("640");

    final Status status = fs.create(model);
    assertThat(status, is(Status.DONE));
  }
}
