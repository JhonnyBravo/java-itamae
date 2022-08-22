package java_itamae.domain.service.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java_itamae.domain.component.directory.DirectoryComponentImpl;
import java_itamae.domain.component.group.GroupComponentImpl;
import java_itamae.domain.component.mode.ModeComponentImpl;
import java_itamae.domain.component.owner.OwnerComponentImpl;
import java_itamae.domain.model.directory.DirectoryResourceModel;
import javax.inject.Inject;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/** 親ディレクトリが存在しない場合のテスト。 */
public class NotExistParentDirectory {
  @Inject private DirectoryService ds;
  private DirectoryResourceModel model;
  private Path path;
  private Path rootDir;

  @Rule
  public WeldInitiator weld =
      WeldInitiator.from(
              DirectoryServiceImpl.class,
              DirectoryComponentImpl.class,
              OwnerComponentImpl.class,
              GroupComponentImpl.class,
              ModeComponentImpl.class)
          .inject(this)
          .build();

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
   * recursive を指定せずに {@link DirectoryService#create(DirectoryResourceModel)} を実行した場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>ディレクトリが作成されないこと。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void dse001() throws Exception {
    final int status = ds.create(model);
    assertThat(status, is(1));
    assertThat(rootDir.toFile().isDirectory(), is(false));
  }

  /**
   * recursive に true を設定した場合に
   *
   * <ul>
   *   <li>親ディレクトリも含めてディレクトリが作成されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void dss001() throws Exception {
    model.setRecursive("true");
    final int status = ds.create(model);
    assertThat(status, is(2));

    // parent/sub1/sub2
    assertThat(path.toFile().isDirectory(), is(true));
    // parent/sub1
    assertThat(path.getParent().toFile().isDirectory(), is(true));
    // parent
    assertThat(rootDir.toFile().isDirectory(), is(true));
  }
}
