package java_itamae.domain.component.owner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import jakarta.inject.Inject;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java_itamae.domain.component.properties.PropertiesComponentImpl;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.status.Status;
import java_itamae.domain.service.properties.PropertiesService;
import java_itamae.domain.service.properties.PropertiesServiceImpl;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/** ディレクトリ所有者を変更する場合のテスト。 */
public class UpdateDirectoryOwner {
  @Inject private OwnerComponent component;
  @Inject private PropertiesService service;
  private Path path;
  private String owner;

  @Rule
  public WeldInitiator weld =
      WeldInitiator.from(
              OwnerComponentImpl.class, PropertiesServiceImpl.class, PropertiesComponentImpl.class)
          .inject(this)
          .build();

  @Before
  public void setUp() throws Exception {
    path = component.convertToPath("test_dir");
    Files.createDirectory(path);

    final ContentsModel model = new ContentsModel();
    model.setPath("src/test/resources/test.properties");

    service.init(model);
    owner = service.getProperty("owner");
  }

  @After
  public void tearDown() throws Exception {
    Files.delete(path);
  }

  /**
   * 以下の検証を実施する。
   *
   * <ul>
   *   <li>{@link OwnerComponent#updateOwner(String, String)} 実行後の返り値の確認。
   *   <li>所有者の変更確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>ディレクトリ所有者が変更されていること。
   * </ul>
   */
  @Test
  public void ors001() throws Exception {
    final UserPrincipal curOwner = component.getOwner(path.toFile().getPath());
    final Status status = component.updateOwner(path.toFile().getPath(), owner);
    final UserPrincipal newOwner = component.getOwner(path.toFile().getPath());

    assertThat(status, is(Status.DONE));
    assertThat(curOwner.equals(newOwner), is(false));
  }

  /**
   * 新しく設定するディレクトリ所有者のユーザ名と、現在設定されているディレクトリ所有者のユーザ名が同一である場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link OwnerComponent#updateOwner(String, String)} 実行後の返り値の確認。
   *   <li>所有者の変更確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#INIT} が返されること。
   *   <li>ディレクトリ所有者が変更されていないこと。
   * </ul>
   */
  @Test
  public void ors002() throws Exception {
    component.updateOwner(path.toFile().getPath(), owner);
    final UserPrincipal curOwner = component.getOwner(path.toFile().getPath());
    final Status status = component.updateOwner(path.toFile().getPath(), owner);
    final UserPrincipal newOwner = component.getOwner(path.toFile().getPath());

    assertThat(status, is(Status.INIT));
    assertThat(curOwner.equals(newOwner), is(true));
  }

  /**
   * 新しく設定するディレクトリ所有者のユーザ名として、存在しないユーザーの名前を指定した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   *   <li>所有者の変更確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link UserPrincipalNotFoundException} が発生すること。
   *   <li>ディレクトリ所有者が変更されていないこと。
   * </ul>
   */
  @Test(expected = UserPrincipalNotFoundException.class)
  public void ore001() throws Exception {
    final UserPrincipal curOwner = component.getOwner(path.toFile().getPath());

    try {
      component.updateOwner(path.toFile().getPath(), "NotExist");
    } catch (Exception e) {
      final UserPrincipal newOwner = component.getOwner(path.toFile().getPath());
      assertThat(curOwner.equals(newOwner), is(true));
      throw e;
    }
  }

  /**
   * 存在しないディレクトリの所有者を変更しようとした場合の動作検証を実施する。
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
  public void ore002() throws Exception {
    component.updateOwner("NotExist", owner);
  }
}
