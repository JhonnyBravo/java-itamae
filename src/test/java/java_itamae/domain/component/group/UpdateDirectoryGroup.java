package java_itamae.domain.component.group;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.GroupPrincipal;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.service.properties.PropertiesService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** ディレクトリのグループ所有者を変更する場合のテスト。 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateDirectoryGroup {
  @Autowired private GroupComponent component;
  @Autowired private PropertiesService service;
  private Path path;
  private String group;

  @Before
  public void setUp() throws Exception {
    path = component.convertToPath("test_dir");
    Files.createDirectory(path);

    final ContentsModel model = new ContentsModel();
    model.setPath("src/test/resources/test.properties");
    service.init(model);

    group = service.getProperty("group");
  }

  @After
  public void tearDown() throws Exception {
    Files.delete(path);
  }

  /**
   *
   *
   * <ul>
   *   <li>グループ所有者が変更されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void grs001() throws Exception {
    final GroupPrincipal curGroup = component.getGroup(path.toFile().getPath());
    final int status = component.updateGroup(path.toFile().getPath(), group);
    final GroupPrincipal newGroup = component.getGroup(path.toFile().getPath());

    assertThat(status, is(2));
    assertThat(curGroup.equals(newGroup), is(false));
  }

  /**
   * 新しいグループ所有者として設定するグループ名と、現在設定されているグループ所有者のグループ名が同一である場合に
   *
   * <ul>
   *   <li>グループ所有者が変更されていないこと。
   *   <li>終了ステータスが 0 であること。
   * </ul>
   */
  @Test
  public void grs002() throws Exception {
    component.updateGroup(path.toFile().getPath(), group);
    final GroupPrincipal curGroup = component.getGroup(path.toFile().getPath());

    final int status = component.updateGroup(path.toFile().getPath(), group);
    final GroupPrincipal newGroup = component.getGroup(path.toFile().getPath());

    assertThat(status, is(0));
    assertThat(curGroup.equals(newGroup), is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>新しく設定するグループ所有者のグループ名として、存在しないグループの名前を指定した場合に異常終了すること。
   *   <li>グループ所有者が変更されていないこと。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void gre001() throws Exception {
    final GroupPrincipal curGroup = component.getGroup(path.toFile().getPath());
    final int status = component.updateGroup(path.toFile().getPath(), "NotExist");
    final GroupPrincipal newGroup = component.getGroup(path.toFile().getPath());

    assertThat(status, is(1));
    assertThat(curGroup.equals(newGroup), is(true));
  }

  /**
   * 存在しないディレクトリのグループ所有者を変更しようとした場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void gre002() throws Exception {
    final int status = component.updateGroup("NotExist", group);
    assertThat(status, is(1));
  }
}
