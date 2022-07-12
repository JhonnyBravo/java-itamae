package java_itamae.domain.repository.owner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.service.properties.PropertiesService;
import java_itamae.domain.service.properties.PropertiesServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ファイル所有者を変更する場合のテスト。 */
public class UpdateFileOwner {
  private OwnerRepository or;
  private File file;
  private String owner;

  @Before
  public void setUp() throws Exception {
    or = new OwnerRepositoryImpl();
    file = new File("test.txt");
    file.mkdir();

    final ContentsModel attr = new ContentsModel();
    attr.setPath("src/test/resources/test.properties");
    final PropertiesService ps = new PropertiesServiceImpl(attr);

    owner = ps.getProperty("owner");
  }

  @After
  public void tearDown() throws Exception {
    file.delete();
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
  public void ors001() throws Exception {
    final boolean status = or.updateOwner("test.txt", owner);
    assertThat(status, is(true));
  }

  /** 新しく設定するファイル所有者のユーザ名と、現在設定されているファイル所有者のユーザ名が同一である場合に終了ステータスが false であること。 */
  @Test
  public void ors002() throws Exception {
    or.updateOwner("test.txt", owner);
    final boolean status = or.updateOwner("test.txt", owner);

    assertThat(status, is(false));
  }

  /**
   * 新しく設定するファイル所有者のユーザ名として、存在しないユーザーの名前を指定した場合に {@link UserPrincipalNotFoundException} が送出されること。
   */
  @Test(expected = UserPrincipalNotFoundException.class)
  public void ore001() throws Exception {
    try {
      or.updateOwner("test.txt", "NotExist");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /** 存在しないファイルの所有者を変更しようとした場合に {@link FileNotFoundException} が送出されること。 */
  @Test(expected = FileNotFoundException.class)
  public void ore002() throws Exception {
    try {
      or.updateOwner("NotExist", owner);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }
}
