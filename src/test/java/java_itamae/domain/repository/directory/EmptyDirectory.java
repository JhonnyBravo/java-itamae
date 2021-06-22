package java_itamae.domain.repository.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.File;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * ディレクトリが存在するが、空である場合のテスト
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(DirectoryRepositoryImpl.class)
public class EmptyDirectory {
  @Inject
  private DirectoryRepository repository;
  private File directory;

  @Before
  public void setUp() throws Exception {
    directory = new File("test_dir/sub1/sub2");
    directory.mkdirs();
  }

  @After
  public void tearDown() throws Exception {
    repository.deleteRecursive("test_dir");
  }

  /**
   * 単一階層のディレクトリを削除できて終了ステータスが true がであること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test1() throws Exception {
    final boolean status = repository.delete("test_dir/sub1/sub2");
    assertThat(status, is(true));
    assertThat(directory.isDirectory(), is(false));
    assertThat(directory.getParentFile().isDirectory(), is(true));
  }

  /**
   * 複数階層のディレクトリを一括削除できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test2() throws Exception {
    final boolean status = repository.deleteRecursive("test_dir");
    final File rootDir = new File("test_dir");
    assertThat(status, is(true));
    assertThat(rootDir.isDirectory(), is(false));
  }
}
