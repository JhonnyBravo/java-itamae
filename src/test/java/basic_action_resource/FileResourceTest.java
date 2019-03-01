package basic_action_resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class FileResourceTest {
    public static class ファイルが存在しない場合 {
        private ActionResource resource;
        private File file;

        @Before
        public void setUp() throws Exception {
            file = new File("test.txt");
            resource = new FileResource("test.txt");
        }

        @After
        public void tearDown() throws Exception {
            if (file.isFile()) {
                file.delete();
            }
        }

        @Test
        public void ファイルを作成できて終了コードが2であること() {
            resource.create();
            assertThat(file.isFile(), is(true));
            assertThat(resource.getCode(), is(2));
        }

        @Test
        public void 何も実行せず終了コードが0であること() {
            resource.delete();
            assertThat(file.isFile(), is(false));
            assertThat(resource.getCode(), is(0));
        }
    }

    public static class ファイルが既に存在する場合 {
        private ActionResource resource;
        private File file;

        @Before
        public void setUp() throws Exception {
            file = new File("test.txt");
            file.createNewFile();

            resource = new FileResource("test.txt");
        }

        @After
        public void tearDown() throws Exception {
            if (file.isFile()) {
                file.delete();
            }
        }

        @Test
        public void ファイルを削除できて終了コードが2であること() {
            resource.delete();
            assertThat(file.isFile(), is(false));
            assertThat(resource.getCode(), is(2));
        }

        @Test
        public void 何も実行せず終了コードが0であること() {
            resource.create();
            assertThat(file.isFile(), is(true));
            assertThat(resource.getCode(), is(0));
        }
    }

    public static class エラーが発生した場合 {
        private ActionResource resource;
        private File file;

        @Before
        public void setUp() throws Exception {
            file = new File("NotExist/test.txt");
            resource = new FileResource("NotExist/test.txt");
        }

        @Test
        public void 処理が中断されて終了コードが1であること() {
            resource.create();
            assertThat(file.isFile(), is(false));
            assertThat(resource.getCode(), is(1));
        }
    }
}
