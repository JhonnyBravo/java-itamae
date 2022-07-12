package java_itamae.domain.repository.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.nio.file.DirectoryNotEmptyException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ディレクトリが既に存在して空ではない場合のテスト */
public class NotEmptyDirectory {
  private DirectoryRepository dr;
  private File directory;
  private File rootDir;
  private final List<File> files = new ArrayList<>();

  @Before
  public void setUp() throws Exception {
    dr = new DirectoryRepositoryImpl();

    rootDir = new File("test_dir");
    directory = new File("test_dir/sub1/sub2");
    directory.mkdirs();

    files.add(new File("test_dir/sub1/test1.txt"));
    files.add(new File("test_dir/sub1/test2.txt"));
    files.add(new File("test_dir/sub1/sub2/test3.txt"));
    files.add(new File("test_dir/sub1/sub2/test4.txt"));

    for (final File file : files) {
      file.createNewFile();
    }
  }

  @After
  public void tearDown() throws Exception {
    files.clear();
    dr.deleteRecursive("test_dir");
  }

  /**
   * {@link DirectoryRepository#delete(String)} 実行時に {@link DirectoryNotEmptyException} が送出されること。
   */
  @Test(expected = DirectoryNotEmptyException.class)
  public void dre001() throws Exception {
    try {
      dr.delete("test_dir");
    } catch (final Exception e) {
      System.err.println(e);
      assertThat(rootDir.isDirectory(), is(true));

      for (final File file : files) {
        assertThat(file.isFile(), is(true));
      }

      throw e;
    }
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link DirectoryRepository#deleteRecursive(String)} 実行時にファイルごとディレクトリが削除されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void drs001() throws Exception {
    final boolean status = dr.deleteRecursive("test_dir");
    assertThat(status, is(true));
    assertThat(rootDir.isDirectory(), is(false));
  }
}
