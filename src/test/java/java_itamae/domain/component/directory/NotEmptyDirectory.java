package java_itamae.domain.component.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java_itamae.domain.model.status.Status;
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
   * 単一階層のディレクトリ削除の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   *   <li>ディレクトリの存在確認。
   *   <li>ディレクトリ配下のファイルの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link DirectoryNotEmptyException} が発生すること。
   *   <li>引数 path に指定されたディレクトリが存在すること。
   *   <li>引数 path に指定されたディレクトリ配下のファイルが存在すること。
   * </ul>
   */
  @Test(expected = DirectoryNotEmptyException.class)
  public void dre001() throws Exception {
    try {
      component.delete(rootDir.toFile().getPath(), false);
    } catch (Exception e) {
      assertThat(rootDir.toFile().isDirectory(), is(true));

      pathList.forEach(
          path -> {
            assertThat(path.toFile().isFile(), is(true));
          });

      throw e;
    }
  }

  /**
   * ディレクトリ一括削除の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link DirectoryComponent#delete(String, boolean)} 実行後の返り値の確認。
   *   <li>ディレクトリの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>引数 path に指定されたディレクトリが存在しないこと。
   * </ul>
   */
  @Test
  public void drs001() throws Exception {
    final Status status = component.delete(rootDir.toFile().getPath(), true);
    assertThat(status, is(Status.DONE));
    assertThat(rootDir.toFile().isDirectory(), is(false));
  }
}
