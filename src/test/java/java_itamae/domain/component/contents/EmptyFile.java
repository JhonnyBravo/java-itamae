package java_itamae.domain.component.contents;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java_itamae.domain.common.GetTestContents;
import java_itamae.domain.common.GetTestEncoding;
import java_itamae.domain.model.contents.ContentsModel;
import javax.inject.Inject;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/** ファイルが空である場合のテスト。 */
public class EmptyFile {
  @Inject private ContentsComponent component;
  @Inject private GetTestEncoding getTestEncoding;
  @Inject private GetTestContents getTestContents;
  private Path path;

  @Rule
  public WeldInitiator weld =
      WeldInitiator.from(ContentsComponentImpl.class, GetTestEncoding.class, GetTestContents.class)
          .inject(this)
          .build();

  @Before
  public void setUp() throws Exception {
    path = component.convertToPath("test.txt");
    Files.createFile(path);
  }

  @After
  public void tearDown() throws Exception {
    Files.delete(path);
  }

  /** {@link ContentsComponent#getContents(ContentsModel)} 実行時に空の {@link List} が返されること。 */
  @Test
  public void crs001() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());

    final List<String> contents = component.getContents(model);
    assertThat(contents.size(), is(0));
  }

  /**
   * {@link ContentsComponent#updateContents(ContentsModel, List)} 実行時に
   *
   * <ul>
   *   <li>ファイルへ書込みができること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void crs002() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());

    final List<String> newContents = getTestContents.get();

    final int status = component.updateContents(model, newContents);
    assertThat(status, is(2));

    final List<String> curContents = component.getContents(model);
    assertThat(curContents.size(), is(2));

    for (int i = 0; i < curContents.size(); i++) {
      assertThat(curContents.get(i), is(newContents.get(i)));
      System.out.println(curContents.get(i));
    }
  }

  /**
   * 文字エンコーディングを指定して {@link ContentsComponent#updateContents(ContentsModel, List)} を実行した場合に
   *
   * <ul>
   *   <li>ファイルへ書込みができること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void crs003() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());
    model.setEncoding(getTestEncoding.get());

    final List<String> newContents = getTestContents.get();

    final int status = component.updateContents(model, newContents);
    assertThat(status, is(2));

    final List<String> curContents = component.getContents(model);
    assertThat(curContents.size(), is(2));

    for (int i = 0; i < curContents.size(); i++) {
      assertThat(curContents.get(i), is(newContents.get(i)));
      System.out.println(curContents.get(i));
    }
  }

  /**
   * {@link ContentsComponent#deleteContents(ContentsModel)} 実行時に
   *
   * <ul>
   *   <li>終了ステータスが 0 であること。
   *   <li>ファイルの内容が更新されないこと。
   * </ul>
   */
  @Test
  public void crs004() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());

    final int status = component.deleteContents(model);
    assertThat(status, is(0));

    final List<String> contents = component.getContents(model);
    assertThat(contents.size(), is(0));
  }
}
