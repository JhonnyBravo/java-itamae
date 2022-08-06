package java_itamae.domain.service.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java_itamae.domain.model.directory.DirectoryResourceModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** ディレクトリを削除する場合のテスト。 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteDirectory {
  @Autowired private DirectoryService service;

  /**
   * 削除対象とするディレクトリが存在しない状態で recursive を指定せずに {@link DirectoryService#delete(DirectoryResourceModel)}
   * を実行した場合に
   *
   * <ul>
   *   <li>操作が実行されないこと。
   *   <li>終了ステータスが 0 であること。
   * </ul>
   */
  @Test
  public void dss001() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath("test_dir");

    final int status = service.delete(model);
    assertThat(status, is(0));
  }

  /**
   * 削除対象とするディレクトリが存在しない状態で recursive に true を設定して {@link
   * DirectoryService#delete(DirectoryResourceModel)} を実行した場合に
   *
   * <ul>
   *   <li>操作が実行されないこと。
   *   <li>終了ステータスが 0 であること。
   * </ul>
   */
  @Test
  public void dss002() throws Exception {
    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath("test_dir");
    model.setRecursive("true");

    final int status = service.delete(model);
    assertThat(status, is(0));
  }

  /**
   * 削除対象とするディレクトリが存在する状態で recursive を指定せずに {@link DirectoryService#delete(DirectoryResourceModel)}
   * を実行した場合に
   *
   * <ul>
   *   <li>ディレクトリが削除されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void dss003() throws Exception {
    final File directory = new File("test_dir");
    directory.mkdir();

    final DirectoryResourceModel model = new DirectoryResourceModel();
    model.setPath("test_dir");

    final int status = service.delete(model);
    assertThat(status, is(2));
    assertThat(directory.isDirectory(), is(false));
  }

  /**
   * 削除対象とするディレクトリが存在する状態で recursive に true を設定して {@link
   * DirectoryService#delete(DirectoryResourceModel)} を実行した場合に
   *
   * <ul>
   *   <li>ディレクトリが一括削除されること。
   *   <li>終了ステータスが 2 であること。
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

    final int status = service.delete(model);

    assertThat(status, is(2));
    assertThat(rootDir.toFile().isDirectory(), is(false));
  }
}
