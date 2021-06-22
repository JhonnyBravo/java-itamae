package java_itamae.domain.service.directory;

import java.io.File;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java_itamae.domain.model.Attribute;
import java_itamae_contents.domain.model.ContentsAttribute;
import java_itamae_properties.domain.service.properties.PropertiesService;
import java_itamae_properties.domain.service.properties.PropertiesServiceImpl;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(DirectoryServiceImpl.class)
public class DirectoryError {
  @Inject
  private DirectoryService ds;
  private PropertiesService ps;
  private File directory;

  @Before
  public void setUp() throws Exception {
    directory = new File("test_dir");

    final ContentsAttribute attr = new ContentsAttribute();
    attr.setPath("src/test/resources/test.properties");
    ps = new PropertiesServiceImpl(attr);
  }

  @After
  public void tearDown() throws Exception {
    if (directory.isDirectory()) {
      directory.delete();
    }
  }

  /**
   * path が指定されないまま {@link DirectoryService#create(Attribute)} を実行した場合に {@link Exception} が送出されること。
   *
   * @throws Exception {@link Exception}
   */
  @Test(expected = Exception.class)
  public void test1() throws Exception {
    final Attribute attr = new Attribute();
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
   * path が指定されないまま {@link DirectoryService#delete(Attribute)} を実行した場合に {@link Exception} が送出されること。
   *
   * @throws Exception {@link Exception}
   */
  @Test(expected = Exception.class)
  public void test2() throws Exception {
    final Attribute attr = new Attribute();

    try {
      ds.delete(attr);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   * 存在しないユーザ名を新しい所有者として指定した場合に {@link UserPrincipalNotFoundException} が送出されること。
   *
   * @throws UserPrincipalNotFoundException {@link UserPrincipalNotFoundException}
   */
  @Test(expected = UserPrincipalNotFoundException.class)
  public void test3() throws Exception {
    final Attribute attr = new Attribute();
    attr.setPath("test_dir");
    attr.setOwner("NowExist");
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
   * 存在しないグループ名を新しいグループ所有者として指定した場合に {@link UserPrincipalNotFoundException} が送出されること。
   *
   * @throws UserPrincipalNotFoundException {@link UserPrincipalNotFoundException}
   */
  @Test(expected = UserPrincipalNotFoundException.class)
  public void test4() throws Exception {
    final Attribute attr = new Attribute();
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

  /**
   * 不正なパーミッション値を新しいパーミッションとして指定した場合に {@link Exception} が送出されること。
   *
   * @throws Exception {@link Exception}
   */
  @Test(expected = Exception.class)
  public void test5() throws Exception {
    final Attribute attr = new Attribute();
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
