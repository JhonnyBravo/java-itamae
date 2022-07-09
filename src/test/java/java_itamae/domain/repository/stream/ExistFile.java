package java_itamae.domain.repository.stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java_itamae.domain.common.GetTestEncoding;
import java_itamae.domain.model.contents.ContentsModel;

/** ファイルが存在する場合のテスト */
public class ExistFile {
  private StreamRepository repository;
  private GetTestEncoding getTestEncoding;
  private File file;

  @Before
  public void setUp() throws Exception {
    repository = new StreamRepositoryImpl();
    getTestEncoding = new GetTestEncoding();

    file = new File("test.txt");
    file.createNewFile();
  }

  @After
  public void tearDown() throws Exception {
    file.delete();
  }

  /**
   * 不正な文字エンコーディングを指定して {@link StreamRepository#getReader(ContentsModel)} を実行した場合に {@link
   * UnsupportedEncodingException} が送出されること。
   */
  @Test(expected = UnsupportedCharsetException.class)
  public void sre001() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath("test.txt");
    model.setEncoding("NotExist");

    try {
      repository.getReader(model);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   * 不正な文字エンコーディングを指定して {@link StreamRepository#getWriter(ContentsModel)} を実行した場合に {@link
   * UnsupportedEncodingException} が送出されること。
   */
  @Test(expected = UnsupportedCharsetException.class)
  public void sre002() throws Exception {
    final ContentsModel attr = new ContentsModel();
    attr.setPath("test.txt");
    attr.setEncoding("NotExist");

    try {
      repository.getWriter(attr);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /** {@link StreamRepository#getReader(ContentsModel)} 実行時に {@link Reader} を取得できること。 */
  @Test
  public void srs001() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath("test.txt");

    try (Reader reader = repository.getReader(model)) {
      assertThat(reader, is(notNullValue()));
      assertThat(model.getEncoding(), is(Charset.defaultCharset().displayName()));
    }
  }

  /** 文字エンコーディングを指定した場合に {@link Reader} を取得できること。 */
  @Test
  public void srs002() throws Exception {
    final String encoding = getTestEncoding.get();

    final ContentsModel model = new ContentsModel();
    model.setPath("test.txt");
    model.setEncoding(encoding);

    try (Reader reader = repository.getReader(model)) {
      assertThat(reader, is(notNullValue()));
      assertThat(model.getEncoding(), is(encoding));
    }
  }

  /** {@link StreamRepository#getWriter(ContentsModel)} 実行時に {@link Writer} を取得できること。 */
  @Test
  public void srs003() throws Exception {
    final ContentsModel model = new ContentsModel();
    model.setPath("test.txt");

    try (Writer writer = repository.getWriter(model)) {
      assertThat(writer, is(notNullValue()));
      assertThat(model.getEncoding(), is(Charset.defaultCharset().displayName()));
    }
  }

  /** 文字エンコーディングを指定した場合に {@link Writer} を取得できること。 */
  @Test
  public void srs004() throws Exception {
    final String encoding = getTestEncoding.get();

    final ContentsModel model = new ContentsModel();
    model.setPath("test.txt");
    model.setEncoding(encoding);

    try (Writer writer = repository.getWriter(model)) {
      assertThat(writer, is(notNullValue()));
      assertThat(model.getEncoding(), is(encoding));
    }
  }
}
