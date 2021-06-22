package java_itamae.domain.service.file;

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
 * ファイルが既に存在して、状態が変わらない場合のテスト。
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(FileServiceImpl.class)
public class NoChangeFileStatus {
  @Inject
  private FileService fs;
  private PropertiesService ps;
  private File file;

  @Before
  public void setUp() throws Exception {
    final ContentsAttribute ca = new ContentsAttribute();
    ca.setPath("src/test/resources/test.properties");
    ps = new PropertiesServiceImpl(ca);

    final Attribute attr = new Attribute();
    attr.setPath("test.txt");
    attr.setOwner(ps.getProperty("owner"));
    attr.setGroup(ps.getProperty("group"));
    attr.setMode("640");
    fs.create(attr);

    file = new File("test.txt");
  }

  @After
  public void tearDown() throws Exception {
    if (file.isFile()) {
      file.delete();
    }
  }

  /**
   * 既に存在するファイルを指定して {@link FileService#create(Attribute)} を実行した場合に終了ステータスが false であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test1() throws Exception {
    final Attribute attr = new Attribute();
    attr.setPath("test.txt");

    final boolean status = fs.create(attr);
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
    attr.setPath("test.txt");
    attr.setOwner(ps.getProperty("owner"));

    final boolean status = fs.create(attr);
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
    attr.setPath("test.txt");
    attr.setGroup(ps.getProperty("group"));

    final boolean status = fs.create(attr);
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
    attr.setPath("test.txt");
    attr.setMode("640");

    final boolean status = fs.create(attr);
    assertThat(status, is(false));
  }
}
