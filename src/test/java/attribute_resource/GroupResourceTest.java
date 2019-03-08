package attribute_resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
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
    public static class ファイルのグループ所有者を変更する場合 {
        private String groupName;
        private File file = new File("test.txt");

        @Before
        public void setUp() throws Exception {
            ContextResource cr = new PropertiesResource("src/test/resources/test.properties");
            cr.openContext();
            Properties p = (Properties) cr.getContext();
            groupName = p.getProperty("groupName");
            cr.closeContext();

            file.createNewFile();
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

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
            String osName = System.getProperty("os.name");
            boolean result;

            if (osName.substring(0, 3).equals("Win")) {
                result = true;
            } else {
                result = false;
            }

            return result;
        }

        @Test
        public void OSがWindowsである場合にエラーとなり終了コードが1であること() {
            if (isWindows()) {
                AttributeResource ar = new GroupResource("test.txt", groupName);
                ar.setAttribute();
                assertThat(ar.getCode(), is(1));
            }
        }

        @Test
        public void ファイルのグループ所有者を変更できて終了コードが2であること() {
            if (!isWindows()) {
                AttributeResource ar = new GroupResource("test.txt", groupName);
                ar.setAttribute();
                assertThat(ar.getCode(), is(2));
            }
        }

        @Test
        public void 新しいグループ所有者として設定するグループ名が現在設定されているグループ所有者のグループ名と同一である場合に終了コードが0であること() {
            if (!isWindows()) {
                AttributeResource ar = new GroupResource("test.txt", groupName);
                ar.setAttribute();
                ar.setAttribute();
                assertThat(ar.getCode(), is(0));
            }
        }

        @Test
        public void 存在しないファイルのグループ所有者を変更しようとした場合にエラーとなり終了コードがで1あること() {
            if (!isWindows()) {
                AttributeResource ar = new GroupResource("NotExist.txt", groupName);
                ar.setAttribute();
                assertThat(ar.getCode(), is(1));
            }
        }

        @Test
        public void 新しいグループ所有者として存在しないグループの名前を設定しようとした場合にエラーとなり終了コードが1であること() {
            if (!isWindows()) {
                AttributeResource ar = new GroupResource("test.txt", "NotExist");
                ar.setAttribute();
                assertThat(ar.getCode(), is(1));
            }
        }
    }

    public static class ディレクトリのグループ所有者を変更する場合 {
        private String groupName;
        private File file = new File("testDir");

        @Before
        public void setUp() throws Exception {
            ContextResource cr = new PropertiesResource("src/test/resources/test.properties");
            cr.openContext();
            Properties p = (Properties) cr.getContext();
            groupName = p.getProperty("groupName");
            cr.closeContext();

            file.mkdir();
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

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
            String osName = System.getProperty("os.name");
            boolean result;

            if (osName.substring(0, 3).equals("Win")) {
                result = true;
            } else {
                result = false;
            }

            return result;
        }

        @Test
        public void OSがWindowsである場合にエラーとなり終了コードが1であること() {
            if (isWindows()) {
                AttributeResource ar = new GroupResource("testDir", groupName);
                ar.setAttribute();
                assertThat(ar.getCode(), is(1));
            }
        }

        @Test
        public void ディレクトリのグループ所有者を変更できて終了コードが2であること() {
            if (!isWindows()) {
                AttributeResource ar = new GroupResource("testDir", groupName);
                ar.setAttribute();
                assertThat(ar.getCode(), is(2));
            }
        }

        @Test
        public void 新しいグループ所有者として設定するグループ名が現在設定されているグループ所有者のグループ名と同一である場合に終了コードが0であること() {
            if (!isWindows()) {
                AttributeResource ar = new GroupResource("testDir", groupName);
                ar.setAttribute();
                ar.setAttribute();
                assertThat(ar.getCode(), is(0));
            }
        }

        @Test
        public void 存在しないディレクトリのグループ所有者を変更しようとした場合にエラーとなり終了コードがで1あること() {
            if (!isWindows()) {
                AttributeResource ar = new GroupResource("NotExist", groupName);
                ar.setAttribute();
                assertThat(ar.getCode(), is(1));
            }
        }

        @Test
        public void 新しいグループ所有者として存在しないグループの名前を設定しようとした場合にエラーとなり終了コードが1であること() {
            if (!isWindows()) {
                AttributeResource ar = new GroupResource("testDir", "NotExist");
                ar.setAttribute();
                assertThat(ar.getCode(), is(1));
            }
        }
    }
}
