package java_itamae.domain.service.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java_itamae.domain.common.IsWindows;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.file.FileResourceModel;
import java_itamae.domain.service.properties.PropertiesService;
import java_itamae.domain.service.properties.PropertiesServiceImpl;

/** ファイルが既に存在する場合のテスト */
public class ExistFile1 {
  private FileService fs;
  private PropertiesService ps;
  private IsWindows isWindows;
  private File file;

  @Before
  public void setUp() throws Exception {
    isWindows = new IsWindows();
    final ContentsModel ca = new ContentsModel();
    ca.setPath("src/test/resources/test.properties");
    ps = new PropertiesServiceImpl(ca);

    final FileResourceModel attr = new FileResourceModel();
    attr.setPath("test.txt");
    attr.setOwner(ps.getProperty("owner"));

    if (!isWindows.get()) {
      attr.setGroup(ps.getProperty("group"));
      attr.setMode("640");
    }

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
   *   <li>何も実行されないこと。
   *   <li>終了ステータスが false であること。
   * </ul>
   */
  @Test
  public void fss001() throws Exception {
    final FileResourceModel attr = new FileResourceModel();
    attr.setPath("test.txt");

    final boolean status = fs.create(attr);
    assertThat(status, is(false));
  }

  /** 新しいファイル所有者のユーザ名と、現在ファイルに設定されている所有者のユーザ名が同一である場合に、終了ステータスが false であること。 */
  @Test
  public void fss002() throws Exception {
    final FileResourceModel attr = new FileResourceModel();
    attr.setPath("test.txt");
    attr.setOwner(ps.getProperty("owner"));

    final boolean status = fs.create(attr);
    assertThat(status, is(false));
  }

  /** 新しいグループ所有者のグループ名と、現在ファイルに設定されているグループ所有者のグループ名が同一である場合に、終了ステータスが false であること。 */
  // @Ignore("Windows の場合は非対応である為、実行しない")
  @Test
  public void fss003() throws Exception {
    final FileResourceModel attr = new FileResourceModel();
    attr.setPath("test.txt");
    attr.setGroup(ps.getProperty("group"));

    final boolean status = fs.create(attr);
    assertThat(status, is(false));
  }

  /** 新しいパーミッション設定値と、現在ファイルに設定されているパーミッション設定値が同一である場合に、終了ステータスが false であること。 */
  // @Ignore("Windows の場合は非対応である為、実行しない")
  @Test
  public void fss004() throws Exception {
    final FileResourceModel attr = new FileResourceModel();
    attr.setPath("test.txt");
    attr.setMode("640");

    final boolean status = fs.create(attr);
    assertThat(status, is(false));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link FileService#delete(FileResourceModel)} 実行時にファイルが削除されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void fss005() throws Exception {
    final FileResourceModel attr = new FileResourceModel();
    attr.setPath("test.txt");

    final boolean status = fs.delete(attr);
    assertThat(status, is(true));
  }
}
