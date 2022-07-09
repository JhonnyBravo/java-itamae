package java_itamae.domain.repository.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java_itamae.domain.common.GetTestEncoding;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.repository.stream.StreamRepository;
import java_itamae.domain.repository.stream.StreamRepositoryImpl;

/** プロパティファイルが空である場合のテスト。 */
public class EmptyFile {
  private PropertiesRepository pr;
  private StreamRepository sr;
  private GetTestEncoding getTestEncoding;
  private File file;

  @Before
  public void setUp() throws Exception {
    pr = new PropertiesRepositoryImpl();
    sr = new StreamRepositoryImpl();
    getTestEncoding = new GetTestEncoding();
    file = new File("test.properties");
    file.createNewFile();
  }

  @After
  public void tearDown() throws Exception {
    file.delete();
  }

  /** {@link PropertiesRepository#getProperties(Reader)} 実行時に空の {@link Map} が返されること。 */
  @Test
  public void prs001() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath("test.properties");

    try (final Reader reader = sr.getReader(model)) {
      final Map<String, String> properties = pr.getProperties(reader);
      assertThat(properties.size(), is(0));
    }
  }

  /**
   * {@link PropertiesRepository#updateProperties(Writer, Map, String)} 実行時にファイルへプロパティの書込みができること。
   */
  @Test
  public void prs002() throws Exception {
    final Map<String, String> newProps = new HashMap<>();
    newProps.put("property1", "1 つ目のプロパティ");
    newProps.put("property2", "2 つ目のプロパティ");

    final ContentsModel model = new ContentsModel();
    model.setPath("test.properties");

    try (final Writer writer = sr.getWriter(model)) {
      final boolean status = pr.updateProperties(writer, newProps, model.getPath());
      assertThat(status, is(true));
    }

    try (Reader reader = sr.getReader(model)) {
      final Map<String, String> curProps = pr.getProperties(reader);
      assertThat(curProps.size(), is(2));
      assertThat(curProps.get("property1"), is("1 つ目のプロパティ"));
      assertThat(curProps.get("property2"), is("2 つ目のプロパティ"));
    }
  }

  /** 文字エンコーディングを指定した場合にプロパティの読み書きができること */
  @Test
  public void prs003() throws Exception {
    final Map<String, String> newProps = new HashMap<>();
    newProps.put("property1", "1 つ目のプロパティ");
    newProps.put("property2", "2 つ目のプロパティ");

    final ContentsModel model = new ContentsModel();
    model.setPath("test.properties");
    model.setEncoding(getTestEncoding.get());

    try (final Writer writer = sr.getWriter(model)) {
      final boolean status = pr.updateProperties(writer, newProps, model.getPath());
      assertThat(status, is(true));
    }

    try (Reader reader = sr.getReader(model)) {
      final Map<String, String> curProps = pr.getProperties(reader);
      assertThat(curProps.size(), is(2));
      assertThat(curProps.get("property1"), is("1 つ目のプロパティ"));
      assertThat(curProps.get("property2"), is("2 つ目のプロパティ"));
    }
  }
}
