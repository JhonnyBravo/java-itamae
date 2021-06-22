package java_itamae.domain.repository.group;

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

@RunWith(CdiRunner.class)
@AdditionalClasses(GroupRepositoryImpl.class)
public class FileGroup {
  @Inject
  private GroupRepository repository;
  private File file;
  private String group;

  @Before
  public void setUp() throws Exception {
    file = new File("test.txt");
    file.createNewFile();

    final ContentsAttribute attr = new ContentsAttribute();
    attr.setPath("src/test/resources/test.properties");

    final PropertiesService ps = new PropertiesServiceImpl(attr);
    group = ps.getProperty("group");
  }

  @After
  public void tearDown() throws Exception {
    file.delete();
  }

  /**
   * グループ所有者の変更ができて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test1() throws Exception {
    final boolean status = repository.updateGroup("test.txt", group);
    assertThat(status, is(true));
  }

  /**
   * 新しく設定するグループ所有者名が、現在設定されているグループ所有者名と同一である場合に、終了ステータスが false であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test2() throws Exception {
    repository.updateGroup("test.txt", group);
    final boolean status = repository.updateGroup("test.txt", group);
    assertThat(status, is(false));
  }

  /**
   * 存在しないグループ名を新しく設定するグループ所有者名として指定した場合に {@link UserPrincipalNotFoundException} が送出されること。
   *
   * @throws UserPrincipalNotFoundException {@link UserPrincipalNotFoundException}
   */
  @Test(expected = UserPrincipalNotFoundException.class)
  public void test3() throws Exception {
    try {
      repository.updateGroup("test.txt", "NotExist");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   * 存在しないファイルのグループ所有者を変更しようとした場合に {@link FileNotFoundException} が送出されること。
   *
   * @throws FileNotFoundException {@link FileNotFoundException}
   */
  @Test(expected = FileNotFoundException.class)
  public void test4() throws Exception {
    try {
      repository.updateGroup("NotExist", group);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }
}
