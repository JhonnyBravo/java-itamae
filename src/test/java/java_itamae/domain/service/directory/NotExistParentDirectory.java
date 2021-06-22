package java_itamae.domain.service.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.File;
import java.nio.file.NoSuchFileException;
import java_itamae.domain.model.Attribute;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 親ディレクトリが存在しない場合のテスト。
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(DirectoryServiceImpl.class)
public class NotExistParentDirectory {
  @Inject
  private DirectoryService service;
  private Attribute attr;

  @Before
  public void setUp() throws Exception {
    attr = new Attribute();
    attr.setPath("parent/sub1/sub2");
  }

  @After
  public void tearDown() throws Exception {
    attr.setPath("parent");
    service.setRecursive(true);
    service.delete(attr);
  }

  /**
   * 親ディレクトリが存在しない状態で {@link DirectoryService#setRecursive(boolean)} を指定せずに
   * {@link DirectoryService#create(Attribute)} を実行した場合に {@link NoSuchFileException} が送出されること。
   *
   * @throws NoSuchFileException {@link NoSuchFileException}
   */
  @Test(expected = NoSuchFileException.class)
  public void test1() throws Exception {
    try {
      service.create(attr);
    } catch (final Exception e) {
      System.err.println(e);
      final File parent = new File("parent");
      assertThat(parent.isDirectory(), is(false));
      throw e;
    }
  }

  /**
   * 複数階層のディレクトリを一括削除できて終了ステータスが true であること。
   *
   * @throws Exception
   */
  @Test
  public void test2() throws Exception {
    final File directory = new File("parent/sub1/sub2");
    directory.mkdirs();

    final Attribute attr = new Attribute();
    attr.setPath("parent");

    service.setRecursive(true);
    final boolean status = service.delete(attr);

    assertThat(status, is(true));
    final File parent = new File("parent");
    assertThat(parent.isDirectory(), is(false));
  }
}
