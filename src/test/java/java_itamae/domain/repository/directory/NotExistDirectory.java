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

@RunWith(CdiRunner.class)
@AdditionalClasses(DirectoryRepositoryImpl.class)
public class NotExistDirectory {
  @Inject
  private DirectoryRepository repository;
  private File rootDir;
  private File subDir;

  @Before
  public void setUp() throws Exception {
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
   * 単一階層のディレクトリを作成できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test1() throws Exception {
    final boolean status = repository.create("test_dir");
    assertThat(status, is(true));
    assertThat(rootDir.isDirectory(), is(true));
  }

  /**
   * 複数階層のディレクトリを一括作成できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test2() throws Exception {
    final boolean status = repository.createRecursive("test_dir/sub_dir");
    assertThat(status, is(true));
    assertThat(rootDir.isDirectory(), is(true));
    assertThat(subDir.isDirectory(), is(true));
  }

  /**
   * {@link DirectoryRepository#delete(String)} 実行時に何も実行せず、終了ステータスが false であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test3() throws Exception {
    final boolean status = repository.delete("test_dir");
    assertThat(status, is(false));
  }

  /**
   * {@link DirectoryRepository#deleteRecursive(String)} 実行時に何も実行せず、終了ステータスが false であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test4() throws Exception {
    final boolean status = repository.deleteRecursive("test_dir");
    assertThat(status, is(false));
  }
}
