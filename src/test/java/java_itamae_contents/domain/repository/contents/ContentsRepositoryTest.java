package java_itamae_contents.domain.repository.contents;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java_itamae_contents.domain.model.ContentsAttribute;
import java_itamae_contents.domain.repository.stream.StreamRepository;
import java_itamae_contents.domain.repository.stream.StreamRepositoryImpl;

@RunWith(Enclosed.class)
public class ContentsRepositoryTest {
    public static class ファイルが存在して空である場合 {
        private ContentsRepository cr;
        private StreamRepository sr;
        private File file;

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
            cr = new ContentsRepositoryImpl();
            sr = new StreamRepositoryImpl();

            file = new File("test.txt");
            file.createNewFile();
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test
        public void getContents実行時に空のListが取得されること() throws Exception {
            final ContentsAttribute attr = new ContentsAttribute();
            attr.setPath("test.txt");

            final Reader reader = sr.getReader(attr);
            final List<String> contents = cr.getContents(reader);

            assertThat(contents.size(), is(0));
        }

        @Test
        public void setContents実行時にファイルへ書込みができて終了ステータスがtrueであること() throws Exception {
            final ContentsAttribute attr = new ContentsAttribute();
            attr.setPath("test.txt");

            final List<String> newContents = new ArrayList<String>();
            newContents.add("1 行目");
            newContents.add("2 行目");

            final Writer writer = sr.getWriter(attr);
            final boolean status = cr.setContents(writer, newContents);
            assertThat(status, is(true));

            final Reader reader = sr.getReader(attr);
            final List<String> curContents = cr.getContents(reader);
            assertThat(curContents.size(), is(2));
            assertThat(curContents.get(0), is("1 行目"));
            assertThat(curContents.get(1), is("2 行目"));

            for (final String line : curContents) {
                System.out.println(line);
            }
        }

        @Test
        public void 文字エンコーディングを指定してファイルの読み書きができること() throws Exception {
            final ContentsAttribute attr = new ContentsAttribute();
            attr.setPath("test.txt");

            if (isWindows()) {
                attr.setEncoding("UTF8");
            } else {
                attr.setEncoding("MS932");
            }

            final List<String> newContents = new ArrayList<String>();
            newContents.add("1 行目");
            newContents.add("2 行目");

            final Writer writer = sr.getWriter(attr);
            final boolean status = cr.setContents(writer, newContents);
            assertThat(status, is(true));

            final Reader reader = sr.getReader(attr);
            final List<String> curContents = cr.getContents(reader);
            assertThat(curContents.size(), is(2));
            assertThat(curContents.get(0), is("1 行目"));
            assertThat(curContents.get(1), is("2 行目"));

            for (final String line : curContents) {
                System.out.println(line);
            }
        }
    }

    public static class ファイルが存在して空ではない場合 {
        private ContentsRepository cr;
        private StreamRepository sr;
        private File file;

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
            file = new File("test.txt");
            file.createNewFile();

            final ContentsAttribute attr = new ContentsAttribute();
            attr.setPath("test.txt");

            final List<String> contents = new ArrayList<String>();
            contents.add("1 行目");
            contents.add("2 行目");

            sr = new StreamRepositoryImpl();
            final Writer writer = sr.getWriter(attr);

            cr = new ContentsRepositoryImpl();
            cr.setContents(writer, contents);
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test
        public void getContents実行時にファイルの内容をList変換して取得できること() throws Exception {
            final ContentsAttribute attr = new ContentsAttribute();
            attr.setPath("test.txt");

            final Reader reader = sr.getReader(attr);
            final List<String> contents = cr.getContents(reader);
            assertThat(contents.size(), is(2));
            assertThat(contents.get(0), is("1 行目"));
            assertThat(contents.get(1), is("2 行目"));

            for (final String line : contents) {
                System.out.println(line);
            }
        }

        @Test
        public void setContents実行時にファイルの内容を上書きできて終了ステータスがtrueであること() throws Exception {
            final ContentsAttribute attr = new ContentsAttribute();
            attr.setPath("test.txt");

            final List<String> newContents = new ArrayList<String>();
            newContents.add("上書きテスト");

            final Writer writer = sr.getWriter(attr);
            final boolean status = cr.setContents(writer, newContents);
            assertThat(status, is(true));

            final Reader reader = sr.getReader(attr);
            final List<String> curContents = cr.getContents(reader);
            assertThat(curContents.size(), is(1));
            assertThat(curContents.get(0), is("上書きテスト"));

            for (final String line : curContents) {
                System.out.println(line);
            }
        }

        @Test
        public void 文字エンコーディングを指定してファイルの読み書きができること() throws Exception {
            final ContentsAttribute attr = new ContentsAttribute();
            attr.setPath("test.txt");

            if (isWindows()) {
                attr.setEncoding("UTF8");
            } else {
                attr.setEncoding("MS932");
            }

            final List<String> newContents = new ArrayList<String>();
            newContents.add("上書きテスト");

            final Writer writer = sr.getWriter(attr);
            final boolean status = cr.setContents(writer, newContents);
            assertThat(status, is(true));

            final Reader reader = sr.getReader(attr);
            final List<String> curContents = cr.getContents(reader);
            assertThat(curContents.size(), is(1));
            assertThat(curContents.get(0), is("上書きテスト"));

            for (final String line : curContents) {
                System.out.println(line);
            }
        }

        @Test
        public void deleteContents実行時にファイルの内容を空にできること() throws Exception {
            final ContentsAttribute attr = new ContentsAttribute();
            attr.setPath("test.txt");

            final Writer writer = sr.getWriter(attr);
            final boolean status = cr.deleteContents(writer);
            assertThat(status, is(true));

            final Reader reader = sr.getReader(attr);
            final List<String> contents = cr.getContents(reader);
            assertThat(contents.size(), is(0));
        }
    }
}
