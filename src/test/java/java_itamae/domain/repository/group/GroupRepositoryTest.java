package java_itamae.domain.repository.group;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java_itamae_contents.domain.model.ContentsAttribute;
import java_itamae_properties.domain.service.properties.PropertiesService;
import java_itamae_properties.domain.service.properties.PropertiesServiceImpl;

@RunWith(Enclosed.class)
public class GroupRepositoryTest {
    public static class ディレクトリのグループ所有者を変更する場合 {
        private GroupRepository gr;
        private File directory;
        private String group;

        @Before
        public void setUp() throws Exception {
            gr = new GroupRepositoryImpl();
            directory = new File("test_dir");
            directory.mkdir();

            final ContentsAttribute attr = new ContentsAttribute();
            attr.setPath("src/test/resources/test.properties");
            final PropertiesService ps = new PropertiesServiceImpl(attr);

            group = ps.getProperty("group");
        }

        @After
        public void tearDown() throws Exception {
            directory.delete();
        }

        @Test
        public void グループ所有者の変更ができて終了ステータスがtrueであること() throws Exception {
            final boolean status = gr.setGroup("test_dir", group);
            assertThat(status, is(true));
        }

        @Test
        public void 新しく設定するグループ所有者のグループ名が現在設定されているグループ所有者のグループ名と同一である場合に終了ステータスがfalseであること() throws Exception {
            gr.setGroup("test_dir", group);
            final boolean status = gr.setGroup("test_dir", group);

            assertThat(status, is(false));
        }

        @Test(expected = UserPrincipalNotFoundException.class)
        public void 新しく設定するグループ所有者として存在しないグループの名前を指定した場合にUserPrincipalNotFoundExceptionが送出されること() throws Exception {
            try {
                gr.setGroup("test_dir", "NotExist");
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }

        @Test(expected = FileNotFoundException.class)
        public void 存在しないディレクトリのグループ所有者を変更しようとした場合にFileNotFoundExceptionが送出されること() throws Exception {
            try {
                gr.setGroup("NotExist", group);
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }
    }

    public static class ファイルのグループ所有者を変更する場合 {
        private GroupRepository gr;
        private File file;
        private String group;

        @Before
        public void setUp() throws Exception {
            gr = new GroupRepositoryImpl();
            file = new File("test.txt");
            file.mkdir();

            final ContentsAttribute attr = new ContentsAttribute();
            attr.setPath("src/test/resources/test.properties");
            final PropertiesService ps = new PropertiesServiceImpl(attr);

            group = ps.getProperty("group");
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test
        public void グループ所有者の変更ができて終了ステータスがtrueであること() throws Exception {
            final boolean status = gr.setGroup("test.txt", group);
            assertThat(status, is(true));
        }

        @Test
        public void 新しく設定するグループ所有者のグループ名が現在設定されているグループ所有者のグループ名と同一である場合に終了ステータスがfalseであること() throws Exception {
            gr.setGroup("test.txt", group);
            final boolean status = gr.setGroup("test.txt", group);

            assertThat(status, is(false));
        }

        @Test(expected = UserPrincipalNotFoundException.class)
        public void 新しく設定するグループ所有者として存在しないグループの名前を指定した場合にUserPrincipalNotFoundExceptionが送出されること() throws Exception {
            try {
                gr.setGroup("test.txt", "NotExist");
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }

        @Test(expected = FileNotFoundException.class)
        public void 存在しないファイルのグループ所有者を変更しようとした場合にFileNotFoundExceptionが送出されること() throws Exception {
            try {
                gr.setGroup("NotExist", group);
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }
    }
}
