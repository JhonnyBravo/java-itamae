package java_itamae.domain.component.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ディレクトリが既に存在して空ではない場合のテスト */
public class NotEmptyDirectory {
  private DirectoryComponent component;
  private Path path;
  private Path rootDir;
  private final List<Path> pathList = new ArrayList<>();

  @Before
  public void setUp() throws Exception {
    component = new DirectoryComponentImpl();

    // ディレクトリ作成
    path = component.convertToPath("test_dir/sub1/sub2");
    rootDir = path.getParent().getParent();

    Files.createDirectories(path);

    // ファイル作成
    // test_dir/sub1
    pathList.add(
        FileSystems.getDefault().getPath(path.getParent().toFile().getPath(), "test1.txt"));
    pathList.add(
        FileSystems.getDefault().getPath(path.getParent().toFile().getPath(), "test2.txt"));
    // test_dir/sub1/sub2
    pathList.add(FileSystems.getDefault().getPath(path.toFile().getPath(), "test3.txt"));
    pathList.add(FileSystems.getDefault().getPath(path.toFile().getPath(), "test4.txt"));

    pathList.forEach(
        path -> {
          try {
            Files.createFile(path);
          } catch (final IOException e) {
            e.printStackTrace();
          }
        });
  }

  @After
  public void tearDown() throws Exception {
    pathList.clear();
    component.delete(rootDir.toFile().getPath(), true);
  }

  /**
   *
   *
   * <ul>
   *   <li>test_dir が削除されていないこと。
   *   <li>test_dir 配下のファイルが削除されていないこと。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void dre001() throws Exception {
    final int status = component.delete(rootDir.toFile().getPath(), false);
    assertThat(status, is(1));
    assertThat(rootDir.toFile().isDirectory(), is(true));
    pathList.forEach(
        path -> {
          assertThat(path.toFile().isFile(), is(true));
        });
  }

  /**
   *
   *
   * <ul>
   *   <li>test_dir が配下のファイルと一緒に削除されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void drs001() throws Exception {
    final int status = component.delete(rootDir.toFile().getPath(), true);
    assertThat(status, is(2));
    assertThat(rootDir.toFile().isDirectory(), is(false));
  }
}
