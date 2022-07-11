package java_itamae.domain.repository.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ディレクトリが存在しない場合のテスト */
public class NotExistDirectory {
  private DirectoryRepository dr;
  private File rootDir;
  private File subDir;

  @Before
  public void setUp() throws Exception {
    dr = new DirectoryRepositoryImpl();
    rootDir = new File("test_dir");
    subDir = new File("test_dir/sub_dir");
  }

  @After
  public void tearDown() throws Exception {
    if (subDir.isDirectory()) {
      subDir.delete();
    }

    if (rootDir.isDirectory()) {
      rootDir.delete();
    }
  }

  /**
   *
   *
   * <ul>
   *   <li>単一階層のディレクトリが作成されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void drs001() throws Exception {
    final boolean status = dr.create("test_dir");
    assertThat(status, is(true));
    assertThat(rootDir.isDirectory(), is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>複数階層のディレクトリが一括作成されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void drs002() throws Exception {
    final boolean status = dr.createRecursive("test_dir/sub_dir");
    assertThat(status, is(true));
    assertThat(subDir.isDirectory(), is(true));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link DirectoryRepository#delete(String)} 実行時に何も実行されないこと。
   *   <li>終了ステータスが false であること。
   * </ul>
   */
  @Test
  public void drs003() throws Exception {
    final boolean status = dr.delete("test_dir");
    assertThat(status, is(false));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link DirectoryRepository#deleteRecursive(String)} 実行時に何も実行されないこと。
   *   <li>終了ステータスが false であること。
   * </ul>
   */
  @Test
  public void drs004() throws Exception {
    final boolean status = dr.deleteRecursive("test_dir");
    assertThat(status, is(false));
  }
}
