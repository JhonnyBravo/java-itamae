package java_itamae.domain.service.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.nio.file.NoSuchFileException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java_itamae.domain.model.directory.DirectoryResourceModel;

/** 親ディレクトリが存在しない場合のテスト。 */
public class NotExistParentDirectory {
  private DirectoryService ds;
  private DirectoryResourceModel attr;

  @Before
  public void setUp() throws Exception {
    ds = new DirectoryServiceImpl();
    attr = new DirectoryResourceModel();
    attr.setPath("parent/sub1/sub2");
  }

  @After
  public void tearDown() throws Exception {
    attr.setPath("parent");
    ds.setRecursive(true);
    ds.delete(attr);
  }

  /** recursive を指定しない場合に {@link NoSuchFileException} が送出されること。 */
  @Test(expected = NoSuchFileException.class)
  public void dse001() throws Exception {
    try {
      ds.create(attr);
    } catch (final Exception e) {
      System.err.println(e);
      final File parent = new File("parent");
      assertThat(parent.isDirectory(), is(false));
      throw e;
    }
  }

  /**
   *
   *
   * <ul>
   *   <li>recursive を true に設定した場合に親ディレクトリも含めてディレクトリが作成されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void dss001() throws Exception {
    ds.setRecursive(true);
    final boolean status = ds.create(attr);
    assertThat(status, is(true));

    final File parent = new File("parent");
    final File sub1 = new File("parent/sub1");
    final File sub2 = new File("parent/sub1/sub2");

    assertThat(parent.isDirectory(), is(true));
    assertThat(sub1.isDirectory(), is(true));
    assertThat(sub2.isDirectory(), is(true));
  }
}
