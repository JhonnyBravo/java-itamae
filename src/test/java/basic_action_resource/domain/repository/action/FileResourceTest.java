package basic_action_resource.domain.repository.action;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import basic_action_resource.domain.repository.action.ActionResource;
import basic_action_resource.domain.repository.action.FileResource;

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
        public void ファイルを作成できて終了ステータスがtrueであること() throws Exception {
            final boolean status = resource.create();
            assertThat(file.isFile(), is(true));
            assertThat(status, is(true));
        }

        @Test
        public void 何も実行せず終了ステータスがfalseであること() {
            final boolean status = resource.delete();
            assertThat(file.isFile(), is(false));
            assertThat(status, is(false));
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
        public void ファイルを削除できて終了ステータスがtrueであること() {
            final boolean status = resource.delete();
            assertThat(file.isFile(), is(false));
            assertThat(status, is(true));
        }

        @Test
        public void 何も実行せず終了ステータスがfalseであること() throws Exception {
            final boolean status = resource.create();
            assertThat(file.isFile(), is(true));
            assertThat(status, is(false));
        }
    }

    public static class エラーが発生した場合 {
        private ActionResource resource;

        @Before
        public void setUp() throws Exception {
            resource = new FileResource("NotExist/test.txt");
        }

        @Test(expected = IOException.class)
        public void 処理が中断されてIOExceptionが送出されること() throws Exception {
            resource.create();
        }
    }
}
