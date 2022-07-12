package java_itamae.domain.repository.contents;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java_itamae.domain.common.GetTestEncoding;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.repository.stream.StreamRepository;
import java_itamae.domain.repository.stream.StreamRepositoryImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** ファイルが空である場合のテスト。 */
public class EmptyFile {
  private ContentsRepository cr;
  private StreamRepository sr;
  private GetTestEncoding getTestEncoding;
  private File file;

  @Before
  public void setUp() throws Exception {
    cr = new ContentsRepositoryImpl();
    sr = new StreamRepositoryImpl();
    getTestEncoding = new GetTestEncoding();
    file = new File("test.txt");
    file.createNewFile();
  }

  @After
  public void tearDown() throws Exception {
    file.delete();
  }

  /** {@link ContentsRepository#getContents(Reader)} 実行時に空の {@link List} が返されること。 */
  @Test
  public void crs001() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath("test.txt");

    final Reader reader = sr.getReader(model);
    final List<String> contents = cr.getContents(reader);

    assertThat(contents.size(), is(0));
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link ContentsRepository#updateContents(Writer, List)} 実行時にファイルへ書込みができること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Test
  public void crs002() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath("test.txt");

    final List<String> newContents = new ArrayList<>();
    newContents.add("1 行目");
    newContents.add("2 行目");

    final Writer writer = sr.getWriter(model);
    final boolean status = cr.updateContents(writer, newContents);
    assertThat(status, is(true));

    final Reader reader = sr.getReader(model);
    final List<String> curContents = cr.getContents(reader);
    assertThat(curContents.size(), is(2));
    assertThat(curContents.get(0), is("1 行目"));
    assertThat(curContents.get(1), is("2 行目"));

    curContents.forEach(
        line -> {
          System.out.println(line);
        });
  }

  /** 文字エンコーディングを指定した場合にファイルの読み書きができること。 */
  @Test
  public void crs003() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath("test.txt");
    model.setEncoding(getTestEncoding.get());

    final List<String> newContents = new ArrayList<>();
    newContents.add("1 行目");
    newContents.add("2 行目");

    final Writer writer = sr.getWriter(model);
    final boolean status = cr.updateContents(writer, newContents);
    assertThat(status, is(true));

    final Reader reader = sr.getReader(model);
    final List<String> curContents = cr.getContents(reader);
    assertThat(curContents.size(), is(2));
    assertThat(curContents.get(0), is("1 行目"));
    assertThat(curContents.get(1), is("2 行目"));

    curContents.forEach(
        line -> {
          System.out.println(line);
        });
  }
}
