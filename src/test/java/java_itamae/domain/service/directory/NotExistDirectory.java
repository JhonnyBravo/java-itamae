package java_itamae.domain.service.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.File;
import java_itamae.domain.model.Attribute;
import java_itamae_contents.domain.model.ContentsAttribute;
import java_itamae_properties.domain.service.properties.PropertiesService;
import java_itamae_properties.domain.service.properties.PropertiesServiceImpl;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * ディレクトリが存在しない場合のテスト。
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(DirectoryServiceImpl.class)
public class NotExistDirectory {
  @Inject
  private DirectoryService ds;
  private PropertiesService ps;
  private File directory;

  @Before
  public void setUp() throws Exception {
    ds.setRecursive(false);
    directory = new File("test_dir");

    final ContentsAttribute attr = new ContentsAttribute();
    attr.setPath("src/test/resources/test.properties");
    ps = new PropertiesServiceImpl(attr);
  }

  @After
  public void tearDown() throws Exception {
    if (directory.isDirectory()) {
      directory.delete();
    }
  }

  /**
   * 単一階層のディレクトリを作成できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test1() throws Exception {
    final Attribute attr = new Attribute();
    attr.setPath("test_dir");

    final boolean status = ds.create(attr);
    assertThat(status, is(true));
    assertThat(directory.isDirectory(), is(true));
  }

  /**
   * 親ディレクトリも含めてディレクトリの作成ができて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test2() throws Exception {
    final Attribute attr = new Attribute();
    attr.setPath("test_dir/sub1/sub2");
    ds.setRecursive(true);
    final boolean status = ds.create(attr);
    assertThat(status, is(true));

    final File parent = new File("test_dir");
    final File sub1 = new File("test_dir/sub1");
    final File sub2 = new File("test_dir/sub1/sub2");

    assertThat(parent.isDirectory(), is(true));
    assertThat(sub1.isDirectory(), is(true));
    assertThat(sub2.isDirectory(), is(true));

    attr.setPath("test_dir");
    ds.delete(attr);
  }

  /**
   * 所有者の変更ができて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test3() throws Exception {
    final Attribute attr = new Attribute();
    attr.setPath("test_dir");
    attr.setOwner(ps.getProperty("owner"));

    final boolean status = ds.create(attr);
    assertThat(status, is(true));
  }

  /**
   * グループ所有者の変更ができて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test4() throws Exception {
    final Attribute attr = new Attribute();
    attr.setPath("test_dir");
    attr.setGroup(ps.getProperty("group"));

    final boolean status = ds.create(attr);
    assertThat(status, is(true));
  }

  /**
   * パーミッション設定の変更ができて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test5() throws Exception {
    final Attribute attr = new Attribute();
    attr.setPath("test_dir");
    attr.setMode("741");

    final boolean status = ds.create(attr);
    assertThat(status, is(true));
  }

  /**
   * 削除対象とするディレクトリが存在しない状態で {@link DirectoryService#setRecursive(boolean)} を指定せずに
   * {@link DirectoryService#delete(Attribute)} を実行した場合に終了ステータスが false であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test6() throws Exception {
    final Attribute attr = new Attribute();
    attr.setPath("test_dir");

    final boolean status = ds.delete(attr);
    assertThat(status, is(false));
  }

  /**
   * 削除対象とするディレクトリが存在しない状態で {@link DirectoryService#setRecursive(boolean)} に true を指定して
   * {@link DirectoryService#delete(Attribute)} を実行した場合に終了ステータスが false であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test7() throws Exception {
    final Attribute attr = new Attribute();
    attr.setPath("test_dir");

    ds.setRecursive(true);
    final boolean status = ds.delete(attr);
    assertThat(status, is(false));
  }
}
