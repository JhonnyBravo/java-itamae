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
        public void 単一階層のディレクトリを作成できて終了コードが2であること() {
            resource.create();
            assertThat(file.isDirectory(), is(true));
            assertThat(resource.getCode(), is(2));
        }

        @Test
        public void 複数階層のディレクトリを一括作成できて終了コードが2であること() {
            file = new File("testDir/sub1/sub2");
            resource = new DirectoryResource("testDir/sub1/sub2");
            resource.create();

            assertThat(file.isDirectory(), is(true));
            assertThat(resource.getCode(), is(2));
        }

        @Test
        public void 何も実行せず終了コードが0であること() {
            resource.delete();
            assertThat(file.isDirectory(), is(false));
            assertThat(resource.getCode(), is(0));
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
        public void 単一階層のディレクトリを削除できて終了コードが2であること() {
            resource.delete();
            assertThat(file.isDirectory(), is(false));
            assertThat(resource.getCode(), is(2));
        }

        @Test
        public void 複数階層のディレクトリを一括削除できて終了コードが2であること() {
            file = new File("testDir/sub1/sub2");
            file.mkdirs();

            resource.delete();

            file = new File("testDir");
            assertThat(file.isDirectory(), is(false));
            assertThat(resource.getCode(), is(2));
        }

        @Test
        public void 何も実行せず終了コードが0であること() {
            resource.create();
            assertThat(file.isDirectory(), is(true));
            assertThat(resource.getCode(), is(0));
        }
    }
}
