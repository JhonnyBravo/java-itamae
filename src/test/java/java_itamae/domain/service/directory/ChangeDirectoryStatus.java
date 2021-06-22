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

@RunWith(CdiRunner.class)
@AdditionalClasses(DirectoryServiceImpl.class)
public class ChangeDirectoryStatus {
  @Inject
  private DirectoryService ds;
  private PropertiesService ps;
  private File directory;

  @Before
  public void setUp() throws Exception {
    final ContentsAttribute ca = new ContentsAttribute();
    ca.setPath("src/test/resources/test.properties");
    ps = new PropertiesServiceImpl(ca);

    directory = new File("test_dir");
    directory.mkdir();
  }

  @After
  public void tearDown() throws Exception {
    if (directory.isDirectory()) {
      directory.delete();
    }
  }

  /**
   * 所有者の変更ができて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test1() throws Exception {
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
  public void test2() throws Exception {
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
  public void test3() throws Exception {
    final Attribute attr = new Attribute();
    attr.setPath("test_dir");
    attr.setMode("741");

    final boolean status = ds.create(attr);
    assertThat(status, is(true));
  }

  /**
   * 単一階層のディレクトリを削除できて終了ステータスが true であること。
   */
  @Test
  public void test4() throws Exception {
    final Attribute attr = new Attribute();
    attr.setPath("test_dir");

    final boolean status = ds.delete(attr);
    assertThat(status, is(true));
    assertThat(directory.isDirectory(), is(false));
  }
}
