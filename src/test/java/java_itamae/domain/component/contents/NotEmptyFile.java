package java_itamae.domain.component.contents;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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

/** ファイルが空ではない場合のテスト。 */
public class NotEmptyFile {
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
    final ContentsModel model = new ContentsModel();
    model.setPath("test.txt");

    path = component.convertToPath(model.getPath());
    Files.createFile(path);

    component.updateContents(model, getTestContents.get());
  }

  @After
  public void tearDown() throws Exception {
    Files.delete(path);
  }

  /**
   * {@link ContentsComponent#getContents(ContentsModel)} 実行時にファイルの内容が {@link List} に変換されて返されること。
   */
  @Test
  public void crs001() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());

    final List<String> newContents = getTestContents.get();
    final List<String> curContents = component.getContents(model);
    assertThat(curContents.size(), is(2));

    for (int i = 0; i < curContents.size(); i++) {
      assertThat(curContents.get(i), is(newContents.get(i)));
      System.out.println(curContents.get(i));
    }
  }

  /**
   * {@link ContentsComponent#updateContents(ContentsModel, List)} 実行時に
   *
   * <ul>
   *   <li>ファイルの内容を上書きできること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void crs002() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());

    final List<String> newContents = new ArrayList<>();
    newContents.add("上書きテスト");

    final int status = component.updateContents(model, newContents);
    assertThat(status, is(2));

    final List<String> curContents = component.getContents(model);
    assertThat(curContents.size(), is(1));

    for (int i = 0; i < curContents.size(); i++) {
      assertThat(curContents.get(i), is(newContents.get(i)));
      System.out.println(curContents.get(i));
    }
  }

  /** 文字エンコーディングを指定した場合にファイルの読み書きができること */
  @Test
  public void crs003() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());
    model.setEncoding(getTestEncoding.get());

    final List<String> newContents = new ArrayList<>();
    newContents.add("上書きテスト");

    final int status = component.updateContents(model, newContents);
    assertThat(status, is(2));

    final List<String> curContents = component.getContents(model);
    assertThat(curContents.size(), is(1));

    for (int i = 0; i < curContents.size(); i++) {
      assertThat(curContents.get(i), is(newContents.get(i)));
      System.out.println(curContents.get(i));
    }
  }

  /**
   * {@link ContentsComponent#deleteContents(ContentsModel)} 実行時に
   *
   * <ul>
   *   <li>ファイルの内容を空にできること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void crs004() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath(path.toFile().getPath());

    final int status = component.deleteContents(model);
    assertThat(status, is(2));

    final List<String> contents = component.getContents(model);
    assertThat(contents.size(), is(0));
  }
}
