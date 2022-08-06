package java_itamae.domain.service.file;

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
import java_itamae.domain.model.file.FileResourceModel;
import java_itamae.domain.service.properties.PropertiesService;

/** ファイルが存在しない場合のテスト */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NotExistFile {
  @Autowired private FileService fs;
  @Autowired private PropertiesService ps;
  private FileResourceModel model;
  private Path path;

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
   *
   *
   * <ul>
   *   <li>ファイルが作成されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void fss001() throws Exception {
    final int status = fs.create(model);
    assertThat(status, is(2));
    assertThat(path.toFile().isFile(), is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>ファイルが作成されること。
   *   <li>ファイル所有者が変更されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void fss002() throws Exception {
    model.setOwner(ps.getProperty("owner"));
    final int status = fs.create(model);
    assertThat(status, is(2));
    assertThat(path.toFile().isFile(), is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>ファイルが作成されること。
   *   <li>ファイルのグループ所有者が変更されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void fss003() throws Exception {
    model.setGroup(ps.getProperty("group"));
    final int status = fs.create(model);
    assertThat(status, is(2));
    assertThat(path.toFile().isFile(), is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>ファイルが作成されること。
   *   <li>ファイルパーミッションが変更されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void fss004() throws Exception {
    model.setMode("721");
    final int status = fs.create(model);
    assertThat(status, is(2));
    assertThat(path.toFile().isFile(), is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link FileService#delete(FileResourceModel)} 実行時に何も実行されないこと。
   *   <li>終了ステータスが 0 であること。
   * </ul>
   */
  @Test
  public void fss005() throws Exception {
    final int status = fs.delete(model);
    assertThat(status, is(0));
    assertThat(path.toFile().isFile(), is(false));
  }
}
