package java_itamae.domain.component.owner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.UserPrincipal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.service.properties.PropertiesService;
import java_itamae.domain.service.properties.PropertiesServiceImpl;

/** ファイル所有者を変更する場合のテスト。 */
public class UpdateFileOwner {
  private OwnerComponent component;
  private Path path;
  private String owner;

  @Before
  public void setUp() throws Exception {
    component = new OwnerComponentImpl();
    path = component.convertToPath("test.txt");
    Files.createFile(path);

    final ContentsModel model = new ContentsModel();
    model.setPath("src/test/resources/test.properties");
    final PropertiesService service = new PropertiesServiceImpl();
    service.init(model);

    owner = service.getProperty("owner");
  }

  @After
  public void tearDown() throws Exception {
    Files.delete(path);
  }

  /**
   *
   *
   * <ul>
   *   <li>ファイル所有者が変更されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void ors001() throws Exception {
    final UserPrincipal curOwner = component.getOwner(path.toFile().getPath());
    final int status = component.updateOwner(path.toFile().getPath(), owner);
    final UserPrincipal newOwner = component.getOwner(path.toFile().getPath());

    assertThat(status, is(2));
    assertThat(curOwner.equals(newOwner), is(false));
  }

  /**
   * 新しく設定するファイル所有者のユーザ名と、現在設定されているファイル所有者のユーザ名が同一である場合に
   *
   * <ul>
   *   <li>所有者が変更されないこと。
   *   <li>終了ステータスが 0 であること。
   * </ul>
   */
  @Test
  public void ors002() throws Exception {
    component.updateOwner(path.toFile().getPath(), owner);
    final UserPrincipal curOwner = component.getOwner(path.toFile().getPath());
    final int status = component.updateOwner(path.toFile().getPath(), owner);
    final UserPrincipal newOwner = component.getOwner(path.toFile().getPath());

    assertThat(status, is(0));
    assertThat(curOwner.equals(newOwner), is(true));
  }

  /**
   * 新しく設定するファイル所有者のユーザ名として、存在しないユーザーの名前を指定した場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>所有者が変更されないこと。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void ore001() throws Exception {
    final UserPrincipal curOwner = component.getOwner(path.toFile().getPath());
    final int status = component.updateOwner(path.toFile().getPath(), "NotExist");
    final UserPrincipal newOwner = component.getOwner(path.toFile().getPath());

    assertThat(status, is(1));
    assertThat(curOwner.equals(newOwner), is(true));
  }

  /**
   * 存在しないファイルの所有者を変更しようとした場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void ore002() throws Exception {
    final int status = component.updateOwner("NotExist", owner);
    assertThat(status, is(1));
  }
}
