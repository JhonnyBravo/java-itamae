package java_itamae.domain.service.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.file.FileResourceModel;
import java_itamae.domain.service.properties.PropertiesService;
import java_itamae.domain.service.properties.PropertiesServiceImpl;

/** ファイルが既に存在する場合のテスト */
public class ExistFile2 {
  private FileService fs;
  private PropertiesService ps;
  private File file;

  @Before
  public void setUp() throws Exception {
    final ContentsModel ca = new ContentsModel();
    ca.setPath("src/test/resources/test.properties");
    ps = new PropertiesServiceImpl(ca);

    final FileResourceModel attr = new FileResourceModel();
    attr.setPath("test.txt");

    fs = new FileServiceImpl();
    fs.create(attr);

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
   *   <li>ファイル所有者が変更されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void fss001() throws Exception {
    final FileResourceModel attr = new FileResourceModel();
    attr.setPath("test.txt");
    attr.setOwner(ps.getProperty("owner"));

    final boolean status = fs.create(attr);
    assertThat(status, is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>グループ所有者が変更されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void fss002() throws Exception {
    final FileResourceModel attr = new FileResourceModel();
    attr.setPath("test.txt");
    attr.setGroup(ps.getProperty("group"));

    final boolean status = fs.create(attr);
    assertThat(status, is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>パーミッション設定値が変更されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test
  public void fss003() throws Exception {
    final FileResourceModel attr = new FileResourceModel();
    attr.setPath("test.txt");
    attr.setMode("640");

    final boolean status = fs.create(attr);
    assertThat(status, is(true));
  }
}
