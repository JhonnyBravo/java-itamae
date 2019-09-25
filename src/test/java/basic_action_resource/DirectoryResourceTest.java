package basic_action_resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class DirectoryResourceTest {
    public static class ディレクトリが存在しない場合 {
        private ActionResource resource;
        private File file;

        @Before
        public void setUp() throws Exception {
            file = new File("testDir");
            resource = new DirectoryResource("testDir");
        }

        @After
        public void tearDown() throws Exception {
            resource = new DirectoryResource("testDir");
            resource.delete();
        }

        @Test
        public void 単一階層のディレクトリを作成できて終了ステータスがtrueであること() throws Exception {
            final boolean status = resource.create();
            assertThat(file.isDirectory(), is(true));
            assertThat(status, is(true));
        }

        @Test
        public void 複数階層のディレクトリを一括作成できて終了ステータスがtrueであること() throws Exception {
            file = new File("testDir/sub1/sub2");
            resource = new DirectoryResource("testDir/sub1/sub2");
            final boolean status = resource.create();

            assertThat(file.isDirectory(), is(true));
            assertThat(status, is(true));
        }

        @Test
        public void 何も実行せず終了ステータスがfalseであること() {
            final boolean status = resource.delete();
            assertThat(file.isDirectory(), is(false));
            assertThat(status, is(false));
        }
    }

    public static class ディレクトリが既に存在する場合 {
        private ActionResource resource;
        private File file;

        @Before
        public void setUp() throws Exception {
            file = new File("testDir");
            file.mkdir();

            resource = new DirectoryResource("testDir");
        }

        @After
        public void tearDown() throws Exception {
            if (file.isDirectory()) {
                file.delete();
            }
        }

        @Test
        public void 単一階層のディレクトリを削除できて終了ステータスがtrueであること() {
            final boolean status = resource.delete();
            assertThat(file.isDirectory(), is(false));
            assertThat(status, is(true));
        }

        @Test
        public void 複数階層のディレクトリを一括削除できて終了ステータスがtrueであること() {
            file = new File("testDir/sub1/sub2");
            file.mkdirs();

            final boolean status = resource.delete();

            file = new File("testDir");
            assertThat(file.isDirectory(), is(false));
            assertThat(status, is(true));
        }

        @Test
        public void 何も実行せず終了ステータスがfalseであること() throws Exception {
            final boolean status = resource.create();
            assertThat(file.isDirectory(), is(true));
            assertThat(status, is(false));
        }
    }

    public static class ファイル一覧取得テスト {
        private DirectoryResource resource;

        @Before
        public void setUp() throws Exception {
            resource = new DirectoryResource("src/test/resources");
        }

        @Test
        public void 拡張子を指定しない場合にディレクトリ直下に存在するファイルを全て一覧取得できること() {
            final File[] files = resource.getFiles();
            assertThat(files.length, is(3));

            for (final File f : files) {
                try {
                    System.out.println(f.getCanonicalPath());
                } catch (final IOException e) {
                    fail("エラーが発生しました。 " + e);
                }
            }
        }

        @Test
        public void 拡張子を指定した場合に該当するファイルのみを一覧取得できること() {
            resource.setFileFilter("txt");
            final File[] files = resource.getFiles();
            assertThat(files.length, is(2));

            for (final File f : files) {
                try {
                    System.out.println(f.getCanonicalPath());
                } catch (final IOException e) {
                    fail("エラーが発生しました。 " + e);
                }
            }
        }

        @Test
        public void ファイルが存在しない場合に空の配列が返ること() {
            resource.setFileFilter("md");
            final File[] files = resource.getFiles();
            assertThat(files.length, is(0));

            for (final File f : files) {
                try {
                    System.out.println(f.getCanonicalPath());
                } catch (final IOException e) {
                    fail("エラーが発生しました。 " + e);
                }
            }
        }

        @Test
        public void ディレクトリが存在しない場合にエラーとなり空の配列が返ること() {
            resource = new DirectoryResource("NotExist");
            final File[] files = resource.getFiles();
            assertThat(files.length, is(0));
        }
    }
}
