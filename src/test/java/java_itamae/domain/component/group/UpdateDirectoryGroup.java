package java_itamae.domain.component.group;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.status.Status;
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
   * 以下の検証を実施する。
   *
   * <ul>
   *   <li>{@link GroupComponent#updateGroup(String, String)} 実行後の返り値の確認。
   *   <li>グループ所有者の変更確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>グループ所有者が変更されていること。
   * </ul>
   */
  @Test
  public void grs001() throws Exception {
    final GroupPrincipal curGroup = component.getGroup(path.toFile().getPath());
    final Status status = component.updateGroup(path.toFile().getPath(), group);
    final GroupPrincipal newGroup = component.getGroup(path.toFile().getPath());

    assertThat(status, is(Status.DONE));
    assertThat(curGroup.equals(newGroup), is(false));
  }

  /**
   * 新しいグループ所有者として設定するグループ名と、現在設定されているグループ所有者のグループ名が同一である場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link GroupComponent#updateGroup(String, String)} 実行後の返り値の確認。
   *   <li>グループ所有者の変更確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#INIT} が返されること。。
   *   <li>グループ所有者が変更されていないこと。
   * </ul>
   */
  @Test
  public void grs002() throws Exception {
    component.updateGroup(path.toFile().getPath(), group);
    final GroupPrincipal curGroup = component.getGroup(path.toFile().getPath());

    final Status status = component.updateGroup(path.toFile().getPath(), group);
    final GroupPrincipal newGroup = component.getGroup(path.toFile().getPath());

    assertThat(status, is(Status.INIT));
    assertThat(curGroup.equals(newGroup), is(true));
  }

  /**
   * 存在しないグループ名を指定した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   *   <li>グループ所有者の変更確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link UserPrincipalNotFoundException} が発生すること。
   *   <li>グループ所有者が変更されていないこと。
   * </ul>
   */
  @Test(expected = UserPrincipalNotFoundException.class)
  public void gre001() throws Exception {
    final GroupPrincipal curGroup = component.getGroup(path.toFile().getPath());

    try {
      component.updateGroup(path.toFile().getPath(), "NotExist");
    } catch (Exception e) {
      final GroupPrincipal newGroup = component.getGroup(path.toFile().getPath());
      assertThat(curGroup.equals(newGroup), is(true));
      throw e;
    }
  }

  /**
   * 存在しないディレクトリのグループ所有者を変更しようとした場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link FileNotFoundException} が発生すること。
   * </ul>
   */
  @Test(expected = FileNotFoundException.class)
  public void gre002() throws Exception {
    component.updateGroup("NotExist", group);
  }
}
