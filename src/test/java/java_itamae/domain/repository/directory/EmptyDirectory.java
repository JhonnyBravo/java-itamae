package java_itamae.domain.repository.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ディレクトリが既に存在するが空である場合のテスト */
public class EmptyDirectory {
  private DirectoryRepository dr;
  private File directory;

  @Before
  public void setUp() throws Exception {
    dr = new DirectoryRepositoryImpl();
    directory = new File("test_dir/sub1/sub2");
    directory.mkdirs();
  }

  @After
  public void tearDown() throws Exception {
    dr.deleteRecursive("test_dir");
  }

  /**
   *
   *
   * <ul>
   *   <li>単一階層のディレクトリが削除されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void drs001() throws Exception {
    final boolean status = dr.delete("test_dir/sub1/sub2");
    assertThat(status, is(true));

    final File subDir = new File("test_dir/sub1/sub2");
    assertThat(subDir.isDirectory(), is(false));
    assertThat(subDir.getParentFile().isDirectory(), is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>複数階層のディレクトリが一括削除されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void drs002() throws Exception {
    final boolean status = dr.deleteRecursive("test_dir");
    assertThat(status, is(true));

    final File rootDir = new File("test_dir");
    assertThat(rootDir.isDirectory(), is(false));
  }
}
