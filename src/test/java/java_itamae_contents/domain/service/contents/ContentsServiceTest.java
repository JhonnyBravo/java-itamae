package java_itamae_contents.domain.service.contents;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java_itamae_contents.domain.model.ContentsAttribute;

@RunWith(Enclosed.class)
public class ContentsServiceTest {
    public static class ファイルが存在しない場合 {
        private File file;
        private ContentsService cs;

        @Before
        public void setUp() throws Exception {
            file = new File("cs_test.txt");

            final ContentsAttribute attr = new ContentsAttribute();
            attr.setPath("cs_test.txt");
            cs = new ContentsServiceImpl(attr);
        }

        @After
        public void tearDown() throws Exception {
            if (file.isFile()) {
                file.delete();
            }
        }

        @Test(expected = FileNotFoundException.class)
        public void getContents実行時にFileNotFoundExceptionが送出されること() throws Exception {
            try {
                cs.getContents();
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }

        @Test(expected = FileNotFoundException.class)
        public void deleteContents実行時にFileNotFoundExceptionが送出されること() throws Exception {
            try {
                cs.deleteContents();
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }

        @Test
        public void setContent実行時にファイルが作成されて文字列の書込みができて終了ステータスがtrueであること() throws Exception {
            final boolean status = cs.setContent("書込みテスト");
            assertThat(status, is(true));
            assertThat(file.isFile(), is(true));

            final List<String> contents = cs.getContents();
            assertThat(contents.size(), is(1));
            assertThat(contents.get(0), is("書込みテスト"));

            System.out.println(contents.get(0));
        }

        @Test
        public void appendContent実行時にファイルが作成されて文字列の書込みができて終了ステータスがtrueであること() throws Exception {
            final boolean status = cs.appendContent("追記テスト");
            assertThat(status, is(true));
            assertThat(file.isFile(), is(true));

            final List<String> contents = cs.getContents();
            assertThat(contents.size(), is(1));
            assertThat(contents.get(0), is("追記テスト"));

            System.out.println(contents.get(0));
        }
    }

    public static class ファイルが存在して空である場合 {
        private File file;
        private ContentsService cs;

        @Before
        public void setUp() throws Exception {
            file = new File("cs_test.txt");
            file.createNewFile();

            final ContentsAttribute attr = new ContentsAttribute();
            attr.setPath("cs_test.txt");
            cs = new ContentsServiceImpl(attr);
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test
        public void getContents実行時に空のListが取得されること() throws Exception {
            final List<String> contents = cs.getContents();
            assertThat(contents.size(), is(0));
        }

        @Test
        public void deleteContents実行時に終了ステータスがfalseであること() throws Exception {
            final boolean status = cs.deleteContents();
            assertThat(status, is(false));
        }

        @Test
        public void setContent実行時にファイルへ文字列の書込みができて終了ステータスがtrueであること() throws Exception {
            final boolean status = cs.setContent("書込みテスト");
            assertThat(status, is(true));

            final List<String> contents = cs.getContents();
            assertThat(contents.size(), is(1));
            assertThat(contents.get(0), is("書込みテスト"));

            System.out.println(contents.get(0));
        }

        @Test
        public void appendContent実行時にファイルへ文字列の書込みができて終了ステータスがtrueであること() throws Exception {
            final boolean status = cs.appendContent("追記テスト");
            assertThat(status, is(true));

            final List<String> contents = cs.getContents();
            assertThat(contents.size(), is(1));
            assertThat(contents.get(0), is("追記テスト"));

            System.out.println(contents.get(0));
        }
    }

    public static class ファイルが存在して空ではない場合 {
        private File file;
        private ContentsService cs;

        @Before
        public void setUp() throws Exception {
            file = new File("cs_test.txt");
            file.createNewFile();

            final ContentsAttribute attr = new ContentsAttribute();
            attr.setPath("cs_test.txt");

            cs = new ContentsServiceImpl(attr);
            cs.appendContent("1 行目");
            cs.appendContent("2 行目");
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test
        public void getContents実行時にファイルを読込んでList変換して返されること() throws Exception {
            final List<String> contents = cs.getContents();
            assertThat(contents.size(), is(2));
            assertThat(contents.get(0), is("1 行目"));
            assertThat(contents.get(1), is("2 行目"));

            for (final String line : contents) {
                System.out.println(line);
            }
        }

        @Test
        public void deleteContents実行時にファイルの内容を削除できて終了ステータスがtrueであること() throws Exception {
            final boolean status = cs.deleteContents();
            assertThat(status, is(true));

            final List<String> contents = cs.getContents();
            assertThat(contents.size(), is(0));
        }

        @Test
        public void setContent実行時にファイルの上書きができて終了ステータスがtrueであること() throws Exception {
            final boolean status = cs.setContent("上書きテスト");
            assertThat(status, is(true));

            final List<String> contents = cs.getContents();
            assertThat(contents.size(), is(1));
            assertThat(contents.get(0), is("上書きテスト"));

            System.out.println(contents.get(0));
        }

        @Test
        public void appendContent実行時にファイル末尾への追記ができて終了ステータスがtrueであること() throws Exception {
            final boolean status = cs.appendContent("追記テスト");
            assertThat(status, is(true));

            final List<String> contents = cs.getContents();
            assertThat(contents.size(), is(3));
            assertThat(contents.get(2), is("追記テスト"));

            for (final String line : contents) {
                System.out.println(line);
            }
        }
    }
}
