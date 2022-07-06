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
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class DirectoryRepositoryTest {
  public static class ディレクトリが存在しない場合 {
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

    @Test
    public void 単一階層のディレクトリを作成できて終了ステータスがtrueであること() throws Exception {
      final boolean status = dr.create("test_dir");
      assertThat(status, is(true));
      assertThat(rootDir.isDirectory(), is(true));
    }

    @Test
    public void 複数階層のディレクトリを一括作成できて終了ステータスがtrueであること() throws Exception {
      final boolean status = dr.createRecursive("test_dir/sub_dir");
      assertThat(status, is(true));
      assertThat(subDir.isDirectory(), is(true));
    }

    @Test
    public void delete実行時に何も実行せず終了ステータスがfalseであること() throws Exception {
      final boolean status = dr.delete("test_dir");
      assertThat(status, is(false));
    }

    @Test
    public void deleteRecursive実行時に何も実行せず終了ステータスがfalseであること() throws Exception {
      final boolean status = dr.deleteRecursive("test_dir");
      assertThat(status, is(false));
    }
  }

  public static class ディレクトリが既に存在するが空である場合 {
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

    @Test
    public void 単一階層のディレクトリを削除できて終了ステータスがtrueであること() throws Exception {
      final boolean status = dr.delete("test_dir/sub1/sub2");
      assertThat(status, is(true));

      final File subDir = new File("test_dir/sub1/sub2");
      assertThat(subDir.isDirectory(), is(false));
      assertThat(subDir.getParentFile().isDirectory(), is(true));
    }

    @Test
    public void 複数階層のディレクトリを一括削除できて終了ステータスがtrueであること() throws Exception {
      final boolean status = dr.deleteRecursive("test_dir");
      assertThat(status, is(true));

      final File rootDir = new File("test_dir");
      assertThat(rootDir.isDirectory(), is(false));
    }
  }

  public static class ディレクトリが既に存在して空ではない場合 {
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

    @Test(expected = DirectoryNotEmptyException.class)
    public void delete実行時にDirectoryNotEmptyExceptionが送出されること() throws Exception {
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

    @Test
    public void deleteRecursive実行時にファイルごとディレクトリを削除できて終了ステータスがtrueであること() throws Exception {
      final boolean status = dr.deleteRecursive("test_dir");
      assertThat(status, is(true));
      assertThat(rootDir.isDirectory(), is(false));
    }
  }
}
