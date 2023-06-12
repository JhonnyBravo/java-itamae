package java_itamae.domain.component.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java_itamae.domain.model.status.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** ディレクトリが既に存在するが空である場合のテスト */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmptyDirectory {
  @Autowired private DirectoryComponent component;
  private Path path;

  @Before
  public void setUp() throws Exception {
    path = component.convertToPath("test_dir/sub1/sub2");
    Files.createDirectories(path);
  }

  @After
  public void tearDown() throws Exception {
    component.delete(path.getParent().getParent().toFile().getPath(), true);
  }

  /**
   * 単一階層のディレクトリ削除の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link DirectoryComponent#delete(String, boolean)} 実行後の返り値の確認。
   *   <li>ディレクトリの存在確認。
   *   <li>親ディレクトリの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>引数 path に指定されたディレクトリが存在しないこと。
   *   <li>引数 path に指定されたディレクトリの親ディレクトリが存在すること。
   * </ul>
   */
  @Test
  public void drs001() throws Exception {
    final Status status = component.delete(path.toFile().getPath(), false);
    assertThat(status, is(Status.DONE));
    assertThat(path.toFile().isDirectory(), is(false));
    assertThat(path.getParent().toFile().isDirectory(), is(true));
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
  public void drs002() throws Exception {
    final Status status = component.delete(path.getParent().getParent().toFile().getPath(), true);
    assertThat(status, is(Status.DONE));
    assertThat(path.getParent().getParent().toFile().isDirectory(), is(false));
  }
}
