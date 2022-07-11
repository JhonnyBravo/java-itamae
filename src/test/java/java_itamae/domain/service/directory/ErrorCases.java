package java_itamae.domain.service.directory;

import java.io.File;
import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.directory.DirectoryResourceModel;
import java_itamae.domain.service.properties.PropertiesService;
import java_itamae.domain.service.properties.PropertiesServiceImpl;

/** 例外発生時のテスト。 */
public class ErrorCases {
  private DirectoryService ds;
  private PropertiesService ps;
  private File file;

  @Before
  public void setUp() throws Exception {
    ds = new DirectoryServiceImpl();
    file = new File("test_dir");

    final ContentsModel attr = new ContentsModel();
    attr.setPath("src/test/resources/test.properties");
    ps = new PropertiesServiceImpl(attr);
  }

  @After
  public void tearDown() throws Exception {
    if (file.isDirectory()) {
      file.delete();
    }
  }

  /**
   * path が指定されないまま {@link DirectoryService#create(DirectoryResourceModel)} を実行した場合に {@link
   * NullPointerException} が送出されること。
   */
  @Test(expected = NullPointerException.class)
  public void dse001() throws Exception {
    final DirectoryResourceModel attr = new DirectoryResourceModel();
    attr.setOwner(ps.getProperty("owner"));
    attr.setGroup(ps.getProperty("group"));
    attr.setMode("640");

    try {
      ds.create(attr);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   * path が指定されないまま {@link DirectoryService#delete(DirectoryResourceModel)} を実行した場合に {@link
   * NullPointerException} が送出されること。
   */
  @Test(expected = NullPointerException.class)
  public void dse002() throws Exception {
    final DirectoryResourceModel attr = new DirectoryResourceModel();

    try {
      ds.delete(attr);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /** 新しいディレクトリ所有者のユーザ名として存在しないユーザの名前を指定した場合に {@link UserPrincipalNotFoundException} が送出されること。 */
  @Test(expected = UserPrincipalNotFoundException.class)
  public void dse003() throws Exception {
    final DirectoryResourceModel attr = new DirectoryResourceModel();
    attr.setPath("test_dir");
    attr.setOwner("NotExist");
    attr.setGroup(ps.getProperty("group"));
    attr.setMode("640");

    try {
      ds.create(attr);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /** 新しいグループ所有者のグループ名として存在しないグループの名前を指定した場合に {@link UserPrincipalNotFoundException} が送出されること。 */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test(expected = UserPrincipalNotFoundException.class)
  public void dse004() throws Exception {
    final DirectoryResourceModel attr = new DirectoryResourceModel();
    attr.setPath("test_dir");
    attr.setOwner(ps.getProperty("owner"));
    attr.setGroup("NotExist");
    attr.setMode("640");

    try {
      ds.create(attr);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /** 新しいパーミッション設定値として不正なパーミッション値を指定した場合に {@link Exception} が送出されること。 */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test(expected = Exception.class)
  public void dse005() throws Exception {
    final DirectoryResourceModel attr = new DirectoryResourceModel();
    attr.setPath("test_dir");
    attr.setOwner(ps.getProperty("owner"));
    attr.setGroup(ps.getProperty("group"));
    attr.setMode("a40");

    try {
      ds.create(attr);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }
}
