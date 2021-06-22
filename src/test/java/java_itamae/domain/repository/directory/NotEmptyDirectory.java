package java_itamae.domain.repository.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.File;
import java.nio.file.DirectoryNotEmptyException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * ディレクトリが存在するが、空ではない場合のテスト
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(DirectoryRepositoryImpl.class)
public class NotEmptyDirectory {
  @Inject
  private DirectoryRepository repository;
  private File rootDir;
  private final List<File> files = new ArrayList<>();

  @Before
  public void setUp() throws Exception {
    rootDir = new File("test_dir");
    final File subDir = new File("test_dir/sub1/sub2");
    subDir.mkdirs();

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
    repository.deleteRecursive("test_dir");
  }

  /**
   * {@link DirectoryRepository#delete(String)} 実行時に {@link DirectoryNotEmptyException} が送出されること。
   *
   * @throws DirectoryNotEmptyException {@link DirectoryNotEmptyException}
   */
  @Test(expected = DirectoryNotEmptyException.class)
  public void test1() throws Exception {
    try {
      repository.delete("test_dir");
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
   * {@link DirectoryRepository#deleteRecursive(String)} 実行時にファイルごとディレクトリを削除できて終了ステータスが true であること。
   *
   * @throws {@link Exception}
   */
  @Test
  public void test2() throws Exception {
    final boolean status = repository.deleteRecursive("test_dir");
    assertThat(status, is(true));
    assertThat(rootDir.isDirectory(), is(false));
  }
}
