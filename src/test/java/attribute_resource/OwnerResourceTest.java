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
public class OwnerResourceTest {
    public static class ファイルの所有者を変更する場合 {
        private String ownerName;
        private File file = new File("test.txt");

        @Before
        public void setUp() throws Exception {
            ContextResource cr = new PropertiesResource("src/test/resources/test.properties");
            cr.openContext();
            Properties p = (Properties) cr.getContext();
            ownerName = p.getProperty("ownerName");
            cr.closeContext();

            file.createNewFile();
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test
        public void ファイル所有者の変更ができて終了コードが2であること() {
            AttributeResource ar = new OwnerResource("test.txt", ownerName);
            ar.setAttribute();
            assertThat(ar.getCode(), is(2));
        }

        @Test
        public void 新しいファイル所有者として設定するユーザ名が現在のファイル所有者として設定されているユーザ名と同一である場合に終了コードが0であること() {
            AttributeResource ar = new OwnerResource("test.txt", ownerName);
            ar.setAttribute();
            ar.setAttribute();
            assertThat(ar.getCode(), is(0));
        }

        @Test
        public void 存在しないファイルのファイル所有者を変更しようとした場合にエラーとなり終了コードが1であること() {
            AttributeResource ar = new OwnerResource("NotExist.txt", ownerName);
            ar.setAttribute();
            assertThat(ar.getCode(), is(1));
        }

        @Test
        public void 新しいファイル所有者として存在しないユーザの名前を指定した場合にエラーとなり終了コードが1であること() {
            AttributeResource ar = new OwnerResource("test.txt", "NotExist");
            ar.setAttribute();
            assertThat(ar.getCode(), is(1));
        }
    }

    public static class ディレクトリの所有者を変更する場合 {
        private String ownerName;
        private File file = new File("testDir");

        @Before
        public void setUp() throws Exception {
            ContextResource cr = new PropertiesResource("src/test/resources/test.properties");
            cr.openContext();
            Properties p = (Properties) cr.getContext();
            ownerName = p.getProperty("ownerName");
            cr.closeContext();

            file.mkdir();
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test
        public void ディレクトリ所有者の変更ができて終了コードが2であること() {
            AttributeResource ar = new OwnerResource("testDir", ownerName);
            ar.setAttribute();
            assertThat(ar.getCode(), is(2));
        }

        @Test
        public void 新しいディレクトリ所有者として設定するユーザ名が現在のディレクトリ所有者として設定されているユーザ名と同一である場合に終了コードが0であること() {
            AttributeResource ar = new OwnerResource("testDir", ownerName);
            ar.setAttribute();
            ar.setAttribute();
            assertThat(ar.getCode(), is(0));
        }

        @Test
        public void 存在しないディレクトリの所有者を変更しようとした場合にエラーとなり終了コードが1であること() {
            AttributeResource ar = new OwnerResource("NotExist", ownerName);
            ar.setAttribute();
            assertThat(ar.getCode(), is(1));
        }

        @Test
        public void 新しいディレクトリ所有者として存在しないユーザの名前を指定した場合にエラーとなり終了コードが1であること() {
            AttributeResource ar = new OwnerResource("testDir", "NotExist");
            ar.setAttribute();
            assertThat(ar.getCode(), is(1));
        }
    }
}
