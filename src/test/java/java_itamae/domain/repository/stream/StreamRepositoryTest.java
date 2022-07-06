package java_itamae.domain.repository.stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.UnsupportedCharsetException;
import java_itamae.domain.model.contents.ContentsModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class StreamRepositoryTest {
  public static class ファイルが存在しない場合 {
    private ContentsModel attr;
    private StreamRepository repository;

    @Before
    public void setUp() throws Exception {
      repository = new StreamRepositoryImpl();

      attr = new ContentsModel();
      attr.setPath("NotExist.txt");
    }

    @Test(expected = FileNotFoundException.class)
    public void getReader実行時にFileNotFoundExceptionが送出されること() throws Exception {
      try {
        repository.getReader(attr);
      } catch (final Exception e) {
        System.err.println(e);
        throw e;
      }
    }

    @Test(expected = FileNotFoundException.class)
    public void getWriter実行時にFileNotFoundExceptionが送出されること() throws Exception {
      try {
        repository.getWriter(attr);
      } catch (final Exception e) {
        System.err.println(e);
        assertThat(new File(attr.getPath()).isFile(), is(false));
        throw e;
      }
    }
  }

  public static class ファイルが存在する場合 {
    private StreamRepository repository;
    File file;

    private boolean isWindows() {
      boolean result = false;
      final String osName = System.getProperty("os.name");

      if (osName.substring(0, 3).equals("Win")) {
        result = true;
      }

      return result;
    }

    @Before
    public void setUp() throws Exception {
      repository = new StreamRepositoryImpl();

      file = new File("test.txt");
      file.createNewFile();
    }

    @After
    public void tearDown() throws Exception {
      file.delete();
    }

    @Test
    public void getReader実行時にReaderを取得できること() throws Exception {
      final ContentsModel attr = new ContentsModel();
      attr.setPath("test.txt");

      try (Reader reader = repository.getReader(attr)) {
        assertThat(reader, is(notNullValue()));
        assertThat(attr.getEncoding(), is(System.getProperty("file.encoding")));
      }
    }

    @Test
    public void 文字エンコーディングを指定してReaderを取得できること() throws Exception {
      final ContentsModel attr = new ContentsModel();
      attr.setPath("test.txt");

      String encoding = null;

      if (isWindows()) {
        encoding = "UTF8";
      } else {
        encoding = "MS932";
      }

      attr.setEncoding(encoding);

      try (Reader reader = repository.getReader(attr)) {
        assertThat(reader, is(notNullValue()));
        assertThat(attr.getEncoding(), is(encoding));
      }
    }

    @Test(expected = UnsupportedCharsetException.class)
    public void 不正な文字エンコーディングを指定してgetReaderを実行した場合にUnsupportedEncodingExceptionが送出されること()
        throws Exception {
      final ContentsModel attr = new ContentsModel();
      attr.setPath("test.txt");
      attr.setEncoding("NotExist");

      try {
        repository.getReader(attr);
      } catch (final Exception e) {
        System.err.println(e);
        throw e;
      }
    }

    @Test
    public void getWriter実行時にWriterを取得できること() throws Exception {
      final ContentsModel attr = new ContentsModel();
      attr.setPath("test.txt");

      try (Writer writer = repository.getWriter(attr)) {
        assertThat(writer, is(notNullValue()));
        assertThat(attr.getEncoding(), is(System.getProperty("file.encoding")));
      }
    }

    @Test
    public void 文字エンコーディングを指定してWriterを取得できること() throws Exception {
      final ContentsModel attr = new ContentsModel();
      attr.setPath("test.txt");

      String encoding = null;

      if (isWindows()) {
        encoding = "UTF8";
      } else {
        encoding = "MS932";
      }

      attr.setEncoding(encoding);

      try (Writer writer = repository.getWriter(attr)) {
        assertThat(writer, is(notNullValue()));
        assertThat(attr.getEncoding(), is(encoding));
      }
    }

    @Test(expected = UnsupportedCharsetException.class)
    public void 不正な文字エンコーディングを指定してgetWriterを実行した場合にUnsupportedEncodingExceptionが送出されること()
        throws Exception {
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
  }
}
