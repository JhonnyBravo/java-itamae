package java_itamae.domain.service.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.file.FileResourceModel;
import java_itamae.domain.service.properties.PropertiesService;
import java_itamae.domain.service.properties.PropertiesServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ファイルが存在しない場合のテスト */
public class NotExistFile {
  private FileService fs;
  private PropertiesService ps;

  private FileResourceModel attr;
  private File file;

  @Before
  public void setUp() throws Exception {
    final ContentsModel ca = new ContentsModel();
    ca.setPath("src/test/resources/test.properties");
    ps = new PropertiesServiceImpl(ca);

    fs = new FileServiceImpl();
    attr = new FileResourceModel();
    attr.setPath("test.txt");

    file = new File("test.txt");
  }

  @After
  public void tearDown() throws Exception {
    if (file.isFile()) {
      file.delete();
    }
  }

  /**
   *
   *
   * <ul>
   *   <li>ファイルが作成されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void fss001() throws Exception {
    final boolean status = fs.create(attr);
    assertThat(status, is(true));
    assertThat(file.isFile(), is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>ファイルが作成されること。
   *   <li>ファイル所有者が変更されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void fss002() throws Exception {
    attr.setOwner(ps.getProperty("owner"));
    final boolean status = fs.create(attr);
    assertThat(status, is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>ファイルが作成されること。
   *   <li>ファイルのグループ所有者が変更されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void fss003() throws Exception {
    attr.setGroup(ps.getProperty("group"));
    final boolean status = fs.create(attr);
    assertThat(status, is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>ファイルが作成されること。
   *   <li>ファイルパーミッションが変更されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void fss004() throws Exception {
    attr.setMode("721");
    final boolean status = fs.create(attr);
    assertThat(status, is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link FileService#delete(FileResourceModel)} 実行時に何も実行されないこと。
   *   <li>終了ステータスが false であること。
   * </ul>
   */
  @Test
  public void fss005() throws Exception {
    final boolean status = fs.delete(attr);
    assertThat(status, is(false));
    assertThat(file.isFile(), is(false));
  }
}
