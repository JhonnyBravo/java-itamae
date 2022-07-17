package java_itamae.domain.component.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ディレクトリが既に存在するが空である場合のテスト */
public class EmptyDirectory {
  private DirectoryComponent component;
  private Path path;

  @Before
  public void setUp() throws Exception {
    component = new DirectoryComponentImpl();
    path = component.convertToPath("test_dir/sub1/sub2");
    Files.createDirectories(path);
  }

  @After
  public void tearDown() throws Exception {
    component.delete(path.getParent().getParent().toFile().getPath(), true);
  }

  /**
   *
   *
   * <ul>
   *   <li>単一階層のディレクトリが削除されること。
   *   <li>親ディレクトリが削除されないこと。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void drs001() throws Exception {
    final int status = component.delete(path.toFile().getPath(), false);
    assertThat(status, is(2));
    assertThat(path.toFile().isDirectory(), is(false));
    assertThat(path.getParent().toFile().isDirectory(), is(true));
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
    final int status = component.delete(path.getParent().getParent().toFile().getPath(), true);
    assertThat(status, is(2));
    assertThat(path.getParent().getParent().toFile().isDirectory(), is(false));
  }
}