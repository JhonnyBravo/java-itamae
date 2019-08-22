package attribute_resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import context_resource.ContextResource;
import context_resource.PropertiesResource;

@RunWith(Enclosed.class)
public class GroupResourceTest {
    public static class ディレクトリのグループ所有者を変更する場合 {
        private String groupName;
        private final File file = new File("testDir");

        /**
         * OS が Windows であるかどうかを確認する。
         *
         * @return boolean
         *         <ul>
         *         <li>true: OS が Windows であることを表す。</li>
         *         <li>false: OS が Windows ではないことを表す。</li>
         *         </ul>
         */
        private boolean isWindows() {
            final String osName = System.getProperty("os.name");
            boolean result;

            if (osName.substring(0, 3).equals("Win")) {
                result = true;
            } else {
                result = false;
            }

            return result;
        }

        @Test
        public void OSがWindowsである場合にエラーとなり終了ステータスがfalseであること() throws FileNotFoundException, IOException {
            if (isWindows()) {
                final AttributeResource<?> ar = new GroupResource("testDir", groupName);
                final boolean status = ar.setAttribute();
                assertThat(status, is(false));
            }
        }

        @Before
        public void setUp() throws Exception {
            final ContextResource cr = new PropertiesResource("src/test/resources/test.properties");
            cr.openContext();
            final Properties p = (Properties) cr.getContext();
            groupName = p.getProperty("groupName");
            cr.closeContext();

            file.mkdir();
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test
        public void ディレクトリのグループ所有者を変更できて終了ステータスがtrueであること() throws FileNotFoundException, IOException {
            if (!isWindows()) {
                final AttributeResource<?> ar = new GroupResource("testDir", groupName);
                final boolean status = ar.setAttribute();
                assertThat(status, is(true));
            }
        }

        @Test(expected = IOException.class)
        public void 新しいグループ所有者として存在しないグループの名前を設定しようとした場合にエラーとなりIOExceptionが送出されること()
                throws FileNotFoundException, IOException {
            if (!isWindows()) {
                final AttributeResource<?> ar = new GroupResource("testDir", "NotExist");
                ar.setAttribute();
            }
        }

        @Test
        public void 新規設定するグループ所有者のグループ名が現在のグループ所有者のグループ名と同一である場合に終了ステータスがfalseであること()
                throws FileNotFoundException, IOException {
            if (!isWindows()) {
                final AttributeResource<?> ar = new GroupResource("testDir", groupName);
                ar.setAttribute();
                final boolean status = ar.setAttribute();
                assertThat(status, is(false));
            }
        }

        @Test(expected = FileNotFoundException.class)
        public void 存在しないディレクトリのグループ所有者を変更しようとした場合にエラーとなりFileNotFoundExceptionが送出されること()
                throws FileNotFoundException, IOException {
            if (!isWindows()) {
                final AttributeResource<?> ar = new GroupResource("NotExist", groupName);
                ar.setAttribute();
            }
        }
    }

    public static class ファイルのグループ所有者を変更する場合 {
        private String groupName;
        private final File file = new File("test.txt");

        /**
         * OS が Windows であるかどうかを確認する。
         *
         * @return boolean
         *         <ul>
         *         <li>true: OS が Windows であることを表す。</li>
         *         <li>false: OS が Windows ではないことを表す。</li>
         *         </ul>
         */
        private boolean isWindows() {
            final String osName = System.getProperty("os.name");
            boolean result;

            if (osName.substring(0, 3).equals("Win")) {
                result = true;
            } else {
                result = false;
            }

            return result;
        }

        @Test
        public void OSがWindowsである場合にエラーとなり終了ステータスがfalseであること() throws FileNotFoundException, IOException {
            if (isWindows()) {
                final AttributeResource<?> ar = new GroupResource("test.txt", groupName);
                final boolean status = ar.setAttribute();
                assertThat(status, is(false));
            }
        }

        @Before
        public void setUp() throws Exception {
            final ContextResource cr = new PropertiesResource("src/test/resources/test.properties");
            cr.openContext();
            final Properties p = (Properties) cr.getContext();
            groupName = p.getProperty("groupName");
            cr.closeContext();

            file.createNewFile();
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test
        public void ファイルのグループ所有者を変更できて終了ステータスがtrueであること() throws FileNotFoundException, IOException {
            if (!isWindows()) {
                final AttributeResource<?> ar = new GroupResource("test.txt", groupName);
                final boolean status = ar.setAttribute();
                assertThat(status, is(true));
            }
        }

        @Test(expected = IOException.class)
        public void 新しいグループ所有者として存在しないグループの名前を設定しようとした場合にエラーとなりIOExceptionが送出されること()
                throws FileNotFoundException, IOException {
            if (!isWindows()) {
                final AttributeResource<?> ar = new GroupResource("test.txt", "NotExist");
                ar.setAttribute();
            }
        }

        @Test
        public void 新規設定するグループ所有者のグループ名が現在のグループ所有者のグループ名と同一である場合に終了ステータスがfalseであること()
                throws FileNotFoundException, IOException {
            if (!isWindows()) {
                final AttributeResource<?> ar = new GroupResource("test.txt", groupName);
                ar.setAttribute();
                final boolean status = ar.setAttribute();
                assertThat(status, is(false));
            }
        }

        @Test(expected = FileNotFoundException.class)
        public void 存在しないファイルのグループ所有者を変更しようとした場合にエラーとなりFileNotFoundExceptionが送出されること()
                throws FileNotFoundException, IOException {
            if (!isWindows()) {
                final AttributeResource<?> ar = new GroupResource("NotExist.txt", groupName);
                ar.setAttribute();
            }
        }
    }
}
