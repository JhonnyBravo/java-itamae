package java_itamae.domain.repository.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java_itamae.domain.common.GetTestEncoding;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.repository.stream.StreamRepository;
import java_itamae.domain.repository.stream.StreamRepositoryImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** プロパティファイルが空ではない場合のテスト */
public class NotEmptyFile {
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

    final ContentsModel model = new ContentsModel();
    model.setPath("test.properties");

    final Map<String, String> properties = new HashMap<>();
    properties.put("property1", "1 つ目のプロパティ");
    properties.put("property2", "2 つ目のプロパティ");

    try (Writer writer = sr.getWriter(model)) {
      pr.updateProperties(writer, properties, model.getPath());
    }
  }

  @After
  public void tearDown() throws Exception {
    file.delete();
  }

  /** {@link PropertiesRepository#getProperties(Reader)} 実行時にプロパティファイルが {@link Map} に変換されて返されること。 */
  @Test
  public void prs001() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath("test.properties");

    try (final Reader reader = sr.getReader(model)) {
      final Map<String, String> properties = pr.getProperties(reader);
      assertThat(properties.size(), is(2));
      assertThat(properties.get("property1"), is("1 つ目のプロパティ"));
      assertThat(properties.get("property2"), is("2 つ目のプロパティ"));
    }
  }

  /** {@link PropertiesRepository#updateProperties(Writer, Map, String)} 実行時にプロパティファイルの上書きができること。 */
  @Test
  public void prs002() throws Exception {
    final Map<String, String> newProps = new HashMap<>();
    newProps.put("update", "更新テスト");

    final ContentsModel model = new ContentsModel();
    model.setPath("test.properties");

    try (final Writer writer = sr.getWriter(model)) {
      final boolean status = pr.updateProperties(writer, newProps, model.getPath());
      assertThat(status, is(true));
    }

    try (Reader reader = sr.getReader(model)) {
      final Map<String, String> curProps = pr.getProperties(reader);
      assertThat(curProps.size(), is(1));
      assertThat(curProps.get("update"), is("更新テスト"));
    }
  }

  /** 文字エンコーディングを指定した場合にプロパティの読み書きができること。 */
  @Test
  public void prs003() throws Exception {
    final Map<String, String> newProps = new HashMap<>();
    newProps.put("encoding", "文字コードテスト");

    final ContentsModel model = new ContentsModel();
    model.setPath("test.properties");
    model.setEncoding(getTestEncoding.get());

    try (Writer writer = sr.getWriter(model)) {
      final boolean status = pr.updateProperties(writer, newProps, model.getPath());
      assertThat(status, is(true));
    }

    try (Reader reader = sr.getReader(model)) {
      final Map<String, String> curProps = pr.getProperties(reader);
      assertThat(curProps.size(), is(1));
      assertThat(curProps.get("encoding"), is("文字コードテスト"));
    }
  }

  /** {@link PropertiesRepository#deleteProperties(Writer, String)} 実行時にプロパティファイルを空にできること。 */
  @Test
  public void prs004() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath("test.properties");

    try (Writer writer = sr.getWriter(model)) {
      final boolean status = pr.deleteProperties(writer, model.getPath());
      assertThat(status, is(true));
    }

    try (Reader reader = sr.getReader(model)) {
      final Map<String, String> properties = pr.getProperties(reader);
      assertThat(properties.size(), is(0));
    }
  }
}
