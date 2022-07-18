package java_itamae.domain.service.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java_itamae.domain.model.directory.DirectoryResourceModel;

/** 親ディレクトリが存在しない場合のテスト。 */
public class NotExistParentDirectory {
  private DirectoryService ds;
  private DirectoryResourceModel model;
  private Path path;
  private Path rootDir;

  @Before
  public void setUp() throws Exception {
    path = FileSystems.getDefault().getPath("parent/sub1/sub2");
    rootDir = path.getParent().getParent();
    ds = new DirectoryServiceImpl();

    model = new DirectoryResourceModel();
    model.setPath(path.toFile().getPath());
  }

  @After
  public void tearDown() throws Exception {
    model.setPath(rootDir.toFile().getPath());
    model.setRecursive("true");

    ds.delete(model);
  }

  /**
   * recursive を指定せずに {@link DirectoryService#create(DirectoryResourceModel)} を実行した場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>ディレクトリが作成されないこと。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void dse001() throws Exception {
    final int status = ds.create(model);
    assertThat(status, is(1));
    assertThat(rootDir.toFile().isDirectory(), is(false));
  }

  /**
   * recursive に true を設定した場合に
   *
   * <ul>
   *   <li>親ディレクトリも含めてディレクトリが作成されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void dss001() throws Exception {
    model.setRecursive("true");
    final int status = ds.create(model);
    assertThat(status, is(2));

    // parent/sub1/sub2
    assertThat(path.toFile().isDirectory(), is(true));
    // parent/sub1
    assertThat(path.getParent().toFile().isDirectory(), is(true));
    // parent
    assertThat(rootDir.toFile().isDirectory(), is(true));
  }
}
