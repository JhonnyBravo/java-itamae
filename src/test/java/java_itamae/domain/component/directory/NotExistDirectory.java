package java_itamae.domain.component.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import jakarta.inject.Inject;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java_itamae.domain.model.status.Status;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/** ディレクトリが存在しない場合のテスト */
public class NotExistDirectory {
  @Inject private DirectoryComponent component;
  private Path rootDir;
  private Path subDir;

  @Rule
  public WeldInitiator weld = WeldInitiator.from(DirectoryComponentImpl.class).inject(this).build();

  @Before
  public void setUp() throws Exception {
    // test_dir
    rootDir = component.convertToPath("test_dir");
    // test_dir/sub_dir
    subDir = FileSystems.getDefault().getPath(rootDir.toFile().getPath(), "sub_dir");
  }

  @After
  public void tearDown() throws Exception {
    component.delete(rootDir.toFile().getPath(), true);
  }

  /**
   * 単一階層のディレクトリ作成の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link DirectoryComponent#create(String, boolean)} 実行後の返り値の確認。
   *   <li>操作対象とするディレクトリの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>引数 path に指定されたディレクトリが存在すること。
   * </ul>
   */
  @Test
  public void drs001() throws Exception {
    final Status status = component.create(rootDir.toFile().getPath(), false);
    assertThat(status, is(Status.DONE));
    assertThat(rootDir.toFile().isDirectory(), is(true));
  }

  /**
   * ディレクトリ一括作成の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link DirectoryComponent#create(String, boolean)} 実行後の返り値の確認。
   *   <li>操作対象とするディレクトリの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>引数 path に指定されたディレクトリが親ディレクトリも含めて存在すること。
   * </ul>
   */
  @Test
  public void drs002() throws Exception {
    final Status status = component.create(subDir.toFile().getPath(), true);
    assertThat(status, is(Status.DONE));
    assertThat(subDir.toFile().isDirectory(), is(true));
  }

  /**
   * 単一階層のディレクトリ削除の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link DirectoryComponent#delete(String, boolean)} 実行後の返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#INIT} が返されること。
   * </ul>
   */
  @Test
  public void drs003() throws Exception {
    final Status status = component.delete(rootDir.toFile().getPath(), false);
    assertThat(status, is(Status.INIT));
  }

  /**
   * ディレクトリ一括削除の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link DirectoryComponent#delete(String, boolean)} 実行後の返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#INIT} が返されること。
   * </ul>
   */
  @Test
  public void drs004() throws Exception {
    final Status status = component.delete(rootDir.toFile().getPath(), true);
    assertThat(status, is(Status.INIT));
  }
}
