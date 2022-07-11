package java_itamae.domain.service.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import java_itamae.domain.model.directory.DirectoryResourceModel;

/** ディレクトリを削除する場合のテスト。 */
public class DeleteDirectory {
  private DirectoryService ds;

  @Before
  public void setUp() throws Exception {
    ds = new DirectoryServiceImpl();
  }

  /**
   * 削除対象とするディレクトリが存在しない状態で recursive を指定せずに {@link DirectoryService#delete(DirectoryResourceModel)}
   * を実行した場合に終了ステータスが false であること。
   */
  @Test
  public void dss001() throws Exception {
    final DirectoryResourceModel attr = new DirectoryResourceModel();
    attr.setPath("test_dir");

    final boolean status = ds.delete(attr);
    assertThat(status, is(false));
  }

  /**
   * 削除対象とするディレクトリが存在しない状態で recursive に true を設定して {@link
   * DirectoryService#delete(DirectoryResourceModel)} を実行した場合に終了ステータスが false であること。
   */
  @Test
  public void dss002() throws Exception {
    final DirectoryResourceModel attr = new DirectoryResourceModel();
    attr.setPath("test_dir");

    ds.setRecursive(true);
    final boolean status = ds.delete(attr);

    assertThat(status, is(false));
  }

  /**
   *
   *
   * <ul>
   *   <li>削除対象とするディレクトリが存在する状態で recursive を指定せずに {@link
   *       DirectoryService#delete(DirectoryResourceModel)} を実行した場合にディレクトリが削除されること。
   *   <li>終了ステータスが false であること。
   * </ul>
   */
  @Test
  public void dss003() throws Exception {
    final File directory = new File("test_dir");
    directory.mkdir();

    final DirectoryResourceModel attr = new DirectoryResourceModel();
    attr.setPath("test_dir");

    final boolean status = ds.delete(attr);
    assertThat(status, is(true));
    assertThat(directory.isDirectory(), is(false));
  }

  /**
   *
   *
   * <ul>
   *   <li>削除対象とするディレクトリが存在する状態で recursive に true を設定して {@link
   *       DirectoryService#delete(DirectoryResourceModel)} を実行した場合にディレクトリが一括削除されること。
   *   <li>終了ステータスが false であること。
   * </ul>
   */
  @Test
  public void dss004() throws Exception {
    final File directory = new File("parent/sub1/sub2");
    directory.mkdirs();

    final DirectoryResourceModel attr = new DirectoryResourceModel();
    attr.setPath("parent");

    ds.setRecursive(true);
    final boolean status = ds.delete(attr);

    assertThat(status, is(true));
    final File parent = new File("parent");
    assertThat(parent.isDirectory(), is(false));
  }
}
