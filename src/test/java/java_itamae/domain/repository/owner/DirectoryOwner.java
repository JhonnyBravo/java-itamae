package java_itamae.domain.repository.owner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
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
 * ディレクトリに対する所有者変更のテスト。
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(OwnerRepositoryImpl.class)
public class DirectoryOwner {
  @Inject
  private OwnerRepository repository;
  private File directory;
  private String owner;

  @Before
  public void setUp() throws Exception {
    directory = new File("test_dir");
    directory.mkdir();

    final ContentsAttribute attr = new ContentsAttribute();
    attr.setPath("src/test/resources/test.properties");
    final PropertiesService ps = new PropertiesServiceImpl(attr);

    owner = ps.getProperty("owner");
  }

  @After
  public void tearDown() throws Exception {
    directory.delete();
  }

  /**
   * 所有者の変更ができて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test1() throws Exception {
    final boolean status = repository.updateOwner("test_dir", owner);
    assertThat(status, is(true));
  }

  /**
   * 新しく設定する所有者のユーザ名が現在設定されている所有者のユーザ名と同一である場合に終了ステータスが false であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test2() throws Exception {
    repository.updateOwner("test_dir", owner);
    final boolean status = repository.updateOwner("test_dir", owner);
    assertThat(status, is(false));
  }

  /**
   * 存在しないユーザの名前を新しく設定する所有者として指定した場合に {@link UserPrincipalNotFoundException} が送出されること。
   *
   * @throws UserPrincipalNotFoundException {@link UserPrincipalNotFoundException}
   */
  @Test(expected = UserPrincipalNotFoundException.class)
  public void test3() throws Exception {
    try {
      repository.updateOwner("test_dir", "NotExist");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   * 存在しないディレクトリの所有者を変更しようとした場合に {@link FileNotFoundException} が送出されること。
   *
   * @throws FileNotFoundException {@link FileNotFoundException}
   */
  @Test(expected = FileNotFoundException.class)
  public void test4() throws Exception {
    try {
      repository.updateOwner("NotExist", owner);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }
}
