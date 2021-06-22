package java_itamae.domain.service.file;

import java.io.File;
import java.nio.file.NoSuchFileException;
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

/**
 * {@link FileService} のエラーテスト。
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(FileServiceImpl.class)
public class FileError {
  @Inject
  private FileService fs;
  private PropertiesService ps;
  private File file;

  @Before
  public void setUp() throws Exception {
    file = new File("test.txt");

    final ContentsAttribute attr = new ContentsAttribute();
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
   * path が指定されないまま {@link FileService#create(Attribute)} を実行した場合に {@link Exception} が送出されること。
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
      fs.create(attr);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   * path が指定されないまま {@link FileService#delete(Attribute)} を実行した場合に {@link Exception} が送出されること。
   *
   * @throws Exception {@link Exception}
   */
  @Test(expected = Exception.class)
  public void test2() throws Exception {
    final Attribute attr = new Attribute();

    try {
      fs.delete(attr);
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

  /**
   * 存在しないグループ名を新しいグループ所有者として指定した場合に {@link UserPrincipalNotFoundException} が送出されること。
   *
   * @throws UserPrincipalNotFoundException {@link UserPrincipalNotFoundException}
   */
  @Test(expected = UserPrincipalNotFoundException.class)
  public void test4() throws Exception {
    final Attribute attr = new Attribute();
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

  /**
   * 不正なパーミッション値を新しいパーミッションとして指定した場合に {@link Exception} が送出されること。
   *
   * @throws Exception {@link Exception}
   */
  @Test(expected = Exception.class)
  public void test5() throws Exception {
    final Attribute attr = new Attribute();
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

  /**
   * 親ディレクトリが存在しない場合に {@link NoSuchFileException} が送出されること。
   *
   * @throws NoSuchFileException {@link NoSuchFileException}
   */
  @Test(expected = NoSuchFileException.class)
  public void test6() throws Exception {
    Attribute attr=new Attribute();
    attr.setPath("NotExist/test.txt");
    attr.setOwner(ps.getProperty("owner"));
    attr.setGroup(ps.getProperty("group"));
    attr.setMode("640");
    
    try {
      fs.create(attr);
    } catch (Exception e) {
      System.err.println(e);
      throw e;
    }
  }
}
