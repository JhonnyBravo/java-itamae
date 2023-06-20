package java_itamae.domain.service.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import jakarta.inject.Inject;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java_itamae.domain.component.directory.DirectoryComponentImpl;
import java_itamae.domain.component.group.GroupComponentImpl;
import java_itamae.domain.component.mode.ModeComponentImpl;
import java_itamae.domain.component.owner.OwnerComponentImpl;
import java_itamae.domain.model.directory.DirectoryResourceModel;
import java_itamae.domain.model.status.Status;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Rule;
import org.junit.Test;

/** ディレクトリを削除する場合のテスト。 */
public class DeleteDirectory {
  @Inject private DirectoryService ds;

  @Rule
  public WeldInitiator weld =
      WeldInitiator.from(
              DirectoryServiceImpl.class,
              DirectoryComponentImpl.class,
              OwnerComponentImpl.class,
              GroupComponentImpl.class,
              ModeComponentImpl.class)
          .inject(this)
          .build();

  /**
   * 削除対象とするディレクトリが存在しない状態で recursive を指定せずに {@link DirectoryService#delete(DirectoryResourceModel)}
   * を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link DirectoryService#delete(DirectoryResourceModel)} 実行後の返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#INIT} が返されること。
   * </ul>
   */
  @Test
  public void dss001() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath("test_dir");

    final Status status = ds.delete(model);
    assertThat(status, is(Status.INIT));
  }

  /**
   * 削除対象とするディレクトリが存在しない状態で recursive に true を設定して {@link
   * DirectoryService#delete(DirectoryResourceModel)} を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link DirectoryService#delete(DirectoryResourceModel)} 実行後の返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#INIT} が返されること。
   * </ul>
   */
  @Test
  public void dss002() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath("test_dir");
    model.setRecursive("true");

    final Status status = ds.delete(model);
    assertThat(status, is(Status.INIT));
  }

  /**
   * 削除対象とするディレクトリが存在する状態で recursive を指定せずに {@link DirectoryService#delete(DirectoryResourceModel)}
   * を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link DirectoryService#delete(DirectoryResourceModel)} 実行後の返り値の確認。
   *   <li>ディレクトリの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>ディレクトリが削除され、存在しないこと。
   * </ul>
   */
  @Test
  public void dss003() throws Exception {
    final File directory = new File("test_dir");
    directory.mkdir();

    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath("test_dir");

    final Status status = ds.delete(model);
    assertThat(status, is(Status.DONE));
    assertThat(directory.isDirectory(), is(false));
  }

  /**
   * 削除対象とするディレクトリが存在する状態で recursive に true を設定して {@link
   * DirectoryService#delete(DirectoryResourceModel)} を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link DirectoryService#delete(DirectoryResourceModel)} 実行後の返り値の確認。
   *   <li>ディレクトリの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>ディレクトリが削除され、存在しないこと。
   * </ul>
   */
  @Test
  public void dss004() throws Exception {
    // parent/sub1/sub2
    final Path directory = FileSystems.getDefault().getPath("parent/sub1/sub2");
    Files.createDirectories(directory);

    // parent
    final Path rootDir = directory.getParent().getParent();

    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath(rootDir.toFile().getPath());
    model.setRecursive("true");

    final Status status = ds.delete(model);

    assertThat(status, is(Status.DONE));
    assertThat(rootDir.toFile().isDirectory(), is(false));
  }
}
