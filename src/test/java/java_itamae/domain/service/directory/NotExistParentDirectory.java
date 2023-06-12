package java_itamae.domain.service.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.FileSystems;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java_itamae.domain.model.directory.DirectoryResourceModel;
import java_itamae.domain.model.status.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** 親ディレクトリが存在しない場合のテスト。 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NotExistParentDirectory {
  @Autowired private DirectoryService ds;
  private DirectoryResourceModel model;
  private Path path;
  private Path rootDir;

  @Before
  public void setUp() throws Exception {
    path = FileSystems.getDefault().getPath("parent/sub1/sub2");
    rootDir = path.getParent().getParent();

    model = new DirectoryResourceModel();
    model.setPath(path.toFile().getPath());
  }

  @After
  public void tearDown() throws Exception {
    model.setPath(rootDir.toFile().getPath());
    model.setRecursive("true");

    ds.delete(model);
  }

  /**
   * recursive を指定せずに {@link DirectoryService#create(DirectoryResourceModel)} を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認
   *   <li>ディレクトリの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link NoSuchFileException} が発生すること。
   *   <li>ディレクトリが作成されず、存在しないこと。
   * </ul>
   */
  @Test(expected = NoSuchFileException.class)
  public void dse001() throws Exception {
    try {
      ds.create(model);
    } catch (Exception e) {
      assertThat(rootDir.toFile().isDirectory(), is(false));
      throw e;
    }
  }

  /**
   * recursive に true を設定した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link DirectoryService#create(DirectoryResourceModel)} 実行後の返り値の確認。
   *   <li>ディレクトリの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>親ディレクトリも含めてディレクトリが作成されること。
   *   <li>返り値として {@link Status#DONE} が返されること。
   * </ul>
   */
  @Test
  public void dss001() throws Exception {
    model.setRecursive("true");
    final Status status = ds.create(model);
    assertThat(status, is(Status.DONE));

    // parent/sub1/sub2
    assertThat(path.toFile().isDirectory(), is(true));
    // parent/sub1
    assertThat(path.getParent().toFile().isDirectory(), is(true));
    // parent
    assertThat(rootDir.toFile().isDirectory(), is(true));
  }
}
