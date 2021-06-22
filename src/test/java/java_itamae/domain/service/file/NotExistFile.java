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
 * ファイルが存在しない場合のテスト。
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(FileServiceImpl.class)
public class NotExistFile {
  @Inject
  private FileService fs;
  private PropertiesService ps;
  private File file;

  @Before
  public void setUp() throws Exception {
    file = new File("test.txt");

    final ContentsAttribute attr = new ContentsAttribute();
    attr.setPath("src/test/resources/test.properties");
    ps = new PropertiesServiceImpl(attr);
  }

  @After
  public void tearDown() throws Exception {
    if (file.isFile()) {
      file.delete();
    }
  }

  /**
   * ファイルを作成できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test1() throws Exception {
    final Attribute attr = new Attribute();
    attr.setPath("test.txt");

    final boolean status = fs.create(attr);
    assertThat(status, is(true));
    assertThat(file.isFile(), is(true));
  }

  /**
   * 所有者の変更ができて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test3() throws Exception {
    final Attribute attr = new Attribute();
    attr.setPath("test.txt");
    attr.setOwner(ps.getProperty("owner"));

    final boolean status = fs.create(attr);
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
    attr.setPath("test.txt");
    attr.setGroup(ps.getProperty("group"));

    final boolean status = fs.create(attr);
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
    attr.setPath("test.txt");
    attr.setMode("721");

    final boolean status = fs.create(attr);
    assertThat(status, is(true));
  }

  /**
   * 削除対象とするファイルが存在しない状態で {@link FileService#delete(Attribute)} を実行した場合に終了ステータスが false であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test6() throws Exception {
    final Attribute attr = new Attribute();
    attr.setPath("test.txt");

    final boolean status = fs.delete(attr);
    assertThat(status, is(false));
    assertThat(file.isFile(), is(false));
  }
}
