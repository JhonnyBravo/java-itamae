package java_itamae.domain.repository.group;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.service.properties.PropertiesService;
import java_itamae.domain.service.properties.PropertiesServiceImpl;

/** ディレクトリのグループ所有者を変更する場合のテスト。 */
// @Ignore("Windows の場合は非対応である為、実行しない。")
public class UpdateDirectoryGroup {
  private GroupRepository gr;
  private File directory;
  private String group;

  @Before
  public void setUp() throws Exception {
    gr = new GroupRepositoryImpl();
    directory = new File("test_dir");
    directory.mkdir();

    final ContentsModel attr = new ContentsModel();
    attr.setPath("src/test/resources/test.properties");
    final PropertiesService ps = new PropertiesServiceImpl(attr);

    group = ps.getProperty("group");
  }

  @After
  public void tearDown() throws Exception {
    directory.delete();
  }

  /**
   *
   *
   * <ul>
   *   <li>グループ所有者が変更されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void grs001() throws Exception {
    final boolean status = gr.updateGroup("test_dir", group);
    assertThat(status, is(true));
  }

  /** 新しいグループ所有者として設定するグループ名と、現在設定されているグループ所有者のグループ名が同一である場合に終了ステータスが false であること。 */
  @Test
  public void grs002() throws Exception {
    gr.updateGroup("test_dir", group);
    final boolean status = gr.updateGroup("test_dir", group);

    assertThat(status, is(false));
  }

  /**
   * 新しく設定するグループ所有者のグループ名として、存在しないグループの名前を指定した場合に {@link UserPrincipalNotFoundException} が送出されること。
   */
  @Test(expected = UserPrincipalNotFoundException.class)
  public void gre001() throws Exception {
    try {
      gr.updateGroup("test_dir", "NotExist");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /** 存在しないディレクトリのグループ所有者を変更しようとした場合に {@link FileNotFoundException} が送出されること。 */
  @Test(expected = FileNotFoundException.class)
  public void gre002() throws Exception {
    try {
      gr.updateGroup("NotExist", group);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }
}