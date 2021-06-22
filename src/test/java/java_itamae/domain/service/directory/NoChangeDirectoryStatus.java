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
public class NoChangeDirectoryStatus {
  @Inject
  private DirectoryService ds;
  private PropertiesService ps;
  private File directory;

  @Before
  public void setUp() throws Exception {
    final ContentsAttribute ca = new ContentsAttribute();
    ca.setPath("src/test/resources/test.properties");
    ps = new PropertiesServiceImpl(ca);

    final Attribute attr = new Attribute();
    attr.setPath("test_dir");
    attr.setOwner(ps.getProperty("owner"));
    attr.setGroup(ps.getProperty("group"));
    attr.setMode("640");
    ds.create(attr);

    directory = new File("test_dir");
  }

  @After
  public void tearDown() throws Exception {
    if (directory.isDirectory()) {
      directory.delete();
    }
  }

  /**
   * 既に存在するディレクトリを指定して {@link DirectoryService#create(Attribute)} を実行した場合に終了ステータスが false であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test1() throws Exception {
    final Attribute attr = new Attribute();
    attr.setPath("test_dir");

    final boolean status = ds.create(attr);
    assertThat(status, is(false));
  }

  /**
   * 現在設定されている所有者と同一のユーザー名を新しい所有者として指定した場合に終了ステータスが false であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test2() throws Exception {
    final Attribute attr = new Attribute();
    attr.setPath("test_dir");
    attr.setOwner(ps.getProperty("owner"));

    final boolean status = ds.create(attr);
    assertThat(status, is(false));
  }

  /**
   * 現在設定されているグループ所有者と同一のグループ名を新しい所有者として指定した場合に終了ステータスが false であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test3() throws Exception {
    final Attribute attr = new Attribute();
    attr.setPath("test_dir");
    attr.setGroup(ps.getProperty("group"));

    final boolean status = ds.create(attr);
    assertThat(status, is(false));
  }

  /**
   * 現在設定されているパーミッション値と同一の値を新しいパーミッションとして指定した場合に終了ステータスが false であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test4() throws Exception {
    final Attribute attr = new Attribute();
    attr.setPath("test_dir");
    attr.setMode("640");

    final boolean status = ds.create(attr);
    assertThat(status, is(false));
  }
}
