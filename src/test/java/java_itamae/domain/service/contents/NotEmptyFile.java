package java_itamae.domain.service.contents;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java_itamae.domain.common.GetTestContents;
import java_itamae.domain.model.contents.ContentsModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** ファイルが存在して内容が空ではない場合のテスト */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NotEmptyFile {
  private File file;
  @Autowired private ContentsService service;
  @Autowired private GetTestContents getTestContents;

  @Before
  public void setUp() throws Exception {
    file = new File("cs_test.txt");
    file.createNewFile();

    final ContentsModel model = new ContentsModel();
    model.setPath(file.getPath());

    service.init(model);
    service.updateContents(getTestContents.get());
  }

  @After
  public void tearDown() throws Exception {
    file.delete();
  }

  /** {@link ContentsService#getContents()} 実行時にファイルを読込み、内容を {@link List} に変換して返すこと。 */
  @Test
  public void css001() throws Exception {
    final List<String> newContents = getTestContents.get();
    final List<String> curContents = service.getContents();
    assertThat(curContents.size(), is(2));

    for (int i = 0; i < curContents.size(); i++) {
      assertThat(curContents.get(i), is(newContents.get(i)));
      System.out.println(curContents.get(i));
    }
  }

  /**
   * {@link ContentsService#deleteContents()} 実行時に
   *
   * <ul>
   *   <li>ファイルの内容が全削除されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void css002() throws Exception {
    final int status = service.deleteContents();
    assertThat(status, is(2));

    final List<String> contents = service.getContents();
    assertThat(contents.size(), is(0));
  }

  /**
   * {@link ContentsService#updateContents(List)} 実行時に
   *
   * <ul>
   *   <li>ファイルの内容が更新されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Test
  public void css003() throws Exception {
    final List<String> newContents = new ArrayList<>();
    newContents.add("update");

    final int status = service.updateContents(newContents);
    assertThat(status, is(2));

    final List<String> curContents = service.getContents();
    assertThat(curContents.size(), is(1));

    for (int i = 0; i < curContents.size(); i++) {
      assertThat(curContents.get(i), is(newContents.get(i)));
      System.out.println(curContents.get(i));
    }
  }
}
