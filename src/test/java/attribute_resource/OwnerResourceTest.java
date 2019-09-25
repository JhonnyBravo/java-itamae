package attribute_resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import content_resource.ContentResource;
import property_resource.PropertyResource;

@RunWith(Enclosed.class)
public class OwnerResourceTest {
    public static class ディレクトリの所有者を変更する場合 {
        private String ownerName;
        private final File file = new File("testDir");

        @Before
        public void setUp() throws Exception {
            final ContentResource<Map<String, String>> cr = new PropertyResource("src/test/resources/test.properties");
            final Map<String, String> properties = cr.getContent();
            ownerName = properties.get("ownerName");

            file.mkdir();
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test
        public void ディレクトリ所有者の変更ができて終了ステータスがtrueであること() throws Exception {
            final AttributeResource<?> ar = new OwnerResource("testDir", ownerName);
            final boolean status = ar.setAttribute();
            assertThat(status, is(true));
        }

        @Test(expected = IOException.class)
        public void 新しいディレクトリ所有者として存在しないユーザの名前を指定した場合にエラーとなりIOExceptionが送出されること() throws Exception {
            final AttributeResource<?> ar = new OwnerResource("testDir", "NotExist");
            ar.setAttribute();
        }

        @Test
        public void 新規設定するディレクトリ所有者のユーザ名が現在設定されているディレクトリ所有者のユーザ名と同一である場合に終了ステータスがfalseであること() throws Exception {
            final AttributeResource<?> ar = new OwnerResource("testDir", ownerName);
            ar.setAttribute();
            final boolean status = ar.setAttribute();
            assertThat(status, is(false));
        }

        @Test(expected = FileNotFoundException.class)
        public void 存在しないディレクトリの所有者を変更しようとした場合にエラーとなりFileNotFoundExceptionが送出されること() throws Exception {
            final AttributeResource<?> ar = new OwnerResource("NotExist", ownerName);
            ar.setAttribute();
        }
    }

    public static class ファイルの所有者を変更する場合 {
        private String ownerName;
        private final File file = new File("test.txt");

        @Before
        public void setUp() throws Exception {
            final ContentResource<Map<String, String>> cr = new PropertyResource("src/test/resources/test.properties");
            final Map<String, String> properties = cr.getContent();
            ownerName = properties.get("ownerName");

            file.createNewFile();
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test
        public void ファイル所有者の変更ができて終了ステータスがtrueであること() throws Exception {
            final AttributeResource<?> ar = new OwnerResource("test.txt", ownerName);
            final boolean status = ar.setAttribute();
            assertThat(status, is(true));
        }

        @Test(expected = IOException.class)
        public void 新しいファイル所有者として存在しないユーザの名前を指定した場合にエラーとなりIOExceptionが送出されること() throws Exception {
            final AttributeResource<?> ar = new OwnerResource("test.txt", "NotExist");
            ar.setAttribute();
        }

        @Test
        public void 新規設定するファイル所有者のユーザ名が現在のファイル所有者のユーザ名と同一である場合に終了ステータスがfalseであること() throws Exception {
            final AttributeResource<?> ar = new OwnerResource("test.txt", ownerName);
            ar.setAttribute();
            final boolean status = ar.setAttribute();
            assertThat(status, is(false));
        }

        @Test(expected = FileNotFoundException.class)
        public void 存在しないファイルのファイル所有者を変更しようとした場合にエラーとなりFileNotFoundExceptionが送出されること() throws Exception {
            final AttributeResource<?> ar = new OwnerResource("NotExist.txt", ownerName);
            ar.setAttribute();
        }
    }
}
