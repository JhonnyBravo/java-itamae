/**
 *
 */
package java_itamae.domain.repository.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.nio.file.NoSuchFileException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * {@link java_itamae.domain.repository.file.FileRepository} の単体テスト。
 */
@RunWith(Enclosed.class)
public class FileRepositoryTest {
    public static class ファイルが存在しない場合 {
        private FileRepository fr;
        private File file;

        @Before
        public void setUp() throws Exception {
            fr = new FileRepositoryImpl();
            file = new File("test.txt");
        }

        @After
        public void tearDown() throws Exception {
            if (file.isFile()) {
                file.delete();
            }
        }

        @Test
        public void ファイルを作成できて終了ステータスがtrueであること() throws Exception {
            final boolean status = fr.create("test.txt");
            assertThat(status, is(true));
            assertThat(file.isFile(), is(true));
        }

        @Test
        public void 何も実行せず終了ステータスがfalseであること() throws Exception {
            final boolean status = fr.delete("test.txt");
            assertThat(status, is(false));
        }
    }

    public static class ファイルが既に存在する場合 {
        private FileRepository fr;
        private File file;

        @Before
        public void setUp() throws Exception {
            fr = new FileRepositoryImpl();
            file = new File("test.txt");
            file.createNewFile();
        }

        @After
        public void tearDown() throws Exception {
            if (file.isFile()) {
                file.delete();
            }
        }

        @Test
        public void 何も実行せず終了ステータスがfalseであること() throws Exception {
            final boolean status = fr.create("test.txt");
            assertThat(status, is(false));
        }

        @Test
        public void ファイルを削除できて終了ステータスがtrueであること() throws Exception {
            final boolean status = fr.delete("test.txt");
            assertThat(status, is(true));
            assertThat(file.isFile(), is(false));
        }
    }

    public static class 親ディレクトリが存在しない場合 {
        private FileRepository fr;
        private File file;

        @Before
        public void setUp() throws Exception {
            fr = new FileRepositoryImpl();
            file = new File("NotExist/test.txt");
        }

        @Test(expected = NoSuchFileException.class)
        public void 処理が中断されてNoSuchFileExceptionが送出されること() throws Exception {
            try {
                fr.create("NotExist/test.txt");
            } catch (final Exception e) {
                System.err.println(e);
                assertThat(file.isFile(), is(false));
                throw e;
            }
        }
    }
}
