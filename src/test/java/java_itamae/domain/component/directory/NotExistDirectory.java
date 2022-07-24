package java_itamae.domain.component.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ディレクトリが存在しない場合のテスト */
public class NotExistDirectory {
  private DirectoryComponent component;
  private Path rootDir;
  private Path subDir;

  @Before
  public void setUp() throws Exception {
    component = new DirectoryComponentImpl();
    // test_dir
    rootDir = component.convertToPath("test_dir");
    // test_dir/sub_dir
    subDir = FileSystems.getDefault().getPath(rootDir.toFile().getPath(), "sub_dir");
  }

  @After
  public void tearDown() throws Exception {
    component.delete(rootDir.toFile().getPath(), true);
  }

  /**
   *
   *
   * <ul>
   *   <li>単一階層のディレクトリ(test_dir)が作成されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void drs001() throws Exception {
    final int status = component.create(rootDir.toFile().getPath(), false);
    assertThat(status, is(2));
    assertThat(rootDir.toFile().isDirectory(), is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>複数階層のディレクトリ(test_dir/sub_dir)が一括作成されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void drs002() throws Exception {
    final int status = component.create(subDir.toFile().getPath(), true);
    assertThat(status, is(2));
    assertThat(subDir.toFile().isDirectory(), is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link DirectoryComponent#delete(String, boolean)} 実行時に何も実行されないこと。
   *   <li>終了ステータスが 0 であること。
   * </ul>
   */
  @Test
  public void drs003() throws Exception {
    final int status = component.delete(rootDir.toFile().getPath(), false);
    assertThat(status, is(0));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link DirectoryComponent#delete(String, boolean)} 実行時に何も実行されないこと。
   *   <li>終了ステータスが 0 であること。
   * </ul>
   */
  @Test
  public void drs004() throws Exception {
    final int status = component.delete(rootDir.toFile().getPath(), true);
    assertThat(status, is(0));
  }
}
