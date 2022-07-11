package java_itamae.domain.service.file;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.file.FileResourceModel;
import java_itamae.domain.service.properties.PropertiesService;
import java_itamae.domain.service.properties.PropertiesServiceImpl;

/** エラーケースのテスト */
public class ErrorCases {
  private FileService fs;
  private PropertiesService ps;
  private File file;

  @Before
  public void setUp() throws Exception {
    fs = new FileServiceImpl();
    file = new File("test.txt");

    final ContentsModel attr = new ContentsModel();
    attr.setPath("src/test/resources/test.properties");
    ps = new PropertiesServiceImpl(attr);
  }

  @After
  public void tearDown() throws Exception {
    if (file.isFile()) {
      file.delete();
    }
  }

  /**
   * path が指定されないまま {@link FileService#create(FileResourceModel)} を実行した場合に {@link
   * NullPointerException} が送出されること。
   */
  @Test(expected = NullPointerException.class)
  public void fse001() throws Exception {
    final FileResourceModel attr = new FileResourceModel();
    attr.setOwner(ps.getProperty("owner"));
    attr.setGroup(ps.getProperty("group"));
    attr.setMode("640");

    try {
      fs.create(attr);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   * path が指定されないまま {@link FileService#delete(FileResourceModel)} を実行した場合に {@link
   * NullPointerException} が送出されること。
   */
  @Test(expected = NullPointerException.class)
  public void fse002() throws Exception {
    final FileResourceModel attr = new FileResourceModel();

    try {
      fs.delete(attr);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /** 親ディレクトリが存在しない場合に {@link NoSuchFileException} が送出されること。 */
  @Test(expected = NoSuchFileException.class)
  public void fse003() throws Exception {
    final FileResourceModel attr = new FileResourceModel();
    attr.setPath("NotExist/test.txt");
    attr.setOwner(ps.getProperty("owner"));
    attr.setGroup(ps.getProperty("group"));
    attr.setMode("640");

    try {
      fs.create(attr);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /** 新しいファイル所有者として存在しないユーザ名を指定した場合に {@link UserPrincipalNotFoundException} が送出されること。 */
  @Test(expected = UserPrincipalNotFoundException.class)
  public void fse004() throws Exception {
    final FileResourceModel attr = new FileResourceModel();
    attr.setPath("test.txt");
    attr.setOwner("NotExist");
    attr.setGroup(ps.getProperty("group"));
    attr.setMode("640");

    try {
      fs.create(attr);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /** 新しいグループ所有者として存在しないグループ名を指定した場合に {@link UserPrincipalNotFoundException} が送出されること。 */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test(expected = UserPrincipalNotFoundException.class)
  public void fse005() throws Exception {
    final FileResourceModel attr = new FileResourceModel();
    attr.setPath("test.txt");
    attr.setOwner(ps.getProperty("owner"));
    attr.setGroup("NotExist");
    attr.setMode("640");

    try {
      fs.create(attr);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /** 新しいパーミッション設定として不正なパーミッション設定値を指定した場合に {@link Exception} が送出されること。 */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test(expected = Exception.class)
  public void fse006() throws Exception {
    final FileResourceModel attr = new FileResourceModel();
    attr.setPath("test.txt");
    attr.setOwner(ps.getProperty("owner"));
    attr.setGroup(ps.getProperty("group"));
    attr.setMode("a40");

    try {
      fs.create(attr);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }
}
