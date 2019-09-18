package content_resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class TxtResourceTest {
    public static class ファイルが存在しない場合 {
        private final ContentResource<List<String>> resource = new TxtResource("not_exist.txt");

        @Test(expected = FileNotFoundException.class)
        public void getContent実行時にFileNotFoundExceptionが送出されること() throws Exception {
            try {
                resource.getContent();
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }

        @Test(expected = FileNotFoundException.class)
        public void setContent実行時にFileNotFoundExceptionが送出されること() throws Exception {
            final List<String> contents = new ArrayList<String>();
            contents.add("エラーテスト");

            try {
                resource.setContent(contents);
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }
    }

    public static class ファイルが存在するが空である場合 {
        private final ContentResource<List<String>> resource = new TxtResource("test.txt");
        private final File file = new File("test.txt");

        @Before
        public void setUp() throws Exception {
            file.createNewFile();
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test
        public void getContent実行時に空のリストが返されること() throws Exception {
            final List<String> contents = resource.getContent();
            assertThat(contents.size(), is(0));
        }

        @Test
        public void setContent実行時にファイルへ文字列を追記できること() throws Exception {
            final List<String> contents = new ArrayList<String>();
            contents.add("1 行目");
            contents.add("2 行目");

            final boolean status = resource.setContent(contents);
            assertThat(status, is(true));

            final List<String> curContents = resource.getContent();
            assertThat(curContents.size(), is(2));

            for (final String content : curContents) {
                System.out.println(content);
            }
        }

        @Test
        public void 文字エンコーディングを指定してファイルの読み書きができること() throws Exception {
            String encoding = null;
            final String osName = System.getProperty("os.name");

            if (osName.substring(0, 3).equals("Win")) {
                encoding = "UTF8";
            } else {
                encoding = "MS932";
            }

            resource.setEncoding(encoding);

            final List<String> contents = new ArrayList<String>();
            contents.add("1 行目");
            contents.add("2 行目");

            final boolean status = resource.setContent(contents);
            assertThat(status, is(true));

            final List<String> curContents = resource.getContent();
            assertThat(curContents.size(), is(2));

            System.out.println(encoding);

            for (final String content : curContents) {
                System.out.println(content);
            }
        }
    }

    public static class ファイルが存在して空ではない場合 {
        private final ContentResource<List<String>> resource = new TxtResource("test.txt");
        private final File file = new File("test.txt");

        @Before
        public void setUp() throws Exception {
            file.createNewFile();

            final List<String> contents = new ArrayList<String>();
            contents.add("1 行目");
            contents.add("2 行目");
            resource.setContent(contents);
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test
        public void getContent実行時に全行が取得されること() throws Exception {
            final List<String> contents = resource.getContent();
            assertThat(contents.size(), is(2));
        }

        @Test
        public void setContent実行時にファイルの内容を上書きできること() throws Exception {
            final List<String> contents = new ArrayList<String>();
            contents.add("上書きテスト");

            final boolean status = resource.setContent(contents);
            assertThat(status, is(true));

            final List<String> curContents = resource.getContent();
            assertThat(curContents.size(), is(1));

            for (final String content : curContents) {
                System.out.println(content);
            }
        }

        @Test
        public void 文字エンコーディングを指定してファイルの読み書きができること() throws Exception {
            String encoding = null;
            final String osName = System.getProperty("os.name");

            if (osName.substring(0, 3).equals("Win")) {
                encoding = "UTF8";
            } else {
                encoding = "MS932";
            }

            resource.setEncoding(encoding);

            final List<String> contents = new ArrayList<String>();
            contents.add("文字エンコーディング指定テスト");

            final boolean status = resource.setContent(contents);
            assertThat(status, is(true));

            final List<String> curContents = resource.getContent();
            assertThat(curContents.size(), is(1));

            System.out.println(encoding);

            for (final String content : curContents) {
                System.out.println(content);
            }
        }

        @Test
        public void ファイルの内容を空にできること() throws Exception {
            final boolean status = resource.deleteContent();
            assertThat(status, is(true));

            final List<String> contents = resource.getContent();
            assertThat(contents.size(), is(0));
        }
    }

    public static class 不正な文字エンコーディングを指定した場合 {
        private final File file = new File("encoding_test.txt");
        private final ContentResource<List<String>> resource = new TxtResource("encoding_test.txt");

        @Before
        public void setUp() throws Exception {
            file.createNewFile();
            resource.setEncoding("NotExist");
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test(expected = UnsupportedEncodingException.class)
        public void getContent実行時にUnsupportedEncodingExceptionが送出されること() throws Exception {
            try {
                resource.getContent();
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }

        @Test(expected = UnsupportedEncodingException.class)
        public void setContent実行時にUnsupportedEncodingExceptionが送出されること() throws Exception {
            final List<String> contents = new ArrayList<String>();
            contents.add("文字エンコーディングのテスト");

            try {
                resource.setContent(contents);
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }
    }
}
