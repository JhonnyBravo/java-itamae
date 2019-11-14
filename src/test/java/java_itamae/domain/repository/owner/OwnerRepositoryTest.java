package java_itamae.domain.repository.owner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
public class OwnerRepositoryTest {
    public static class ディレクトリの所有者を変更する場合 {
        private OwnerRepository or;
        private File directory;
        private String owner;

        @Before
        public void setUp() throws Exception {
            or = new OwnerRepositoryImpl();
            directory = new File("test_dir");
            directory.mkdir();

            final ContentsAttribute attr = new ContentsAttribute();
            attr.setPath("src/test/resources/test.properties");
            final PropertiesService ps = new PropertiesServiceImpl(attr);

            owner = ps.getProperty("owner");
        }

        @After
        public void tearDown() throws Exception {
            directory.delete();
        }

        @Test
        public void ディレクトリ所有者の変更ができて終了ステータスがtrueであること() throws Exception {
            final boolean status = or.setOwner("test_dir", owner);
            assertThat(status, is(true));
        }

        @Test
        public void 新しく設定するディレクトリ所有者のユーザ名が現在設定されているディレクトリ所有者のユーザ名と同一である場合に終了ステータスがfalseであること() throws Exception {
            or.setOwner("test_dir", owner);
            final boolean status = or.setOwner("test_dir", owner);

            assertThat(status, is(false));
        }

        @Test(expected = UserPrincipalNotFoundException.class)
        public void 新しく設定するディレクトリ所有者として存在しないユーザの名前を指定した場合にUserPrincipalNotFoundExceptionが送出されること() throws Exception {
            try {
                or.setOwner("test_dir", "NotExist");
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }

        @Test(expected = FileNotFoundException.class)
        public void 存在しないディレクトリの所有者を変更しようとした場合にFileNotFoundExceptionが送出されること() throws Exception {
            try {
                or.setOwner("NotExist", owner);
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }
    }

    public static class ファイルの所有者を変更する場合 {
        private OwnerRepository or;
        private File file;
        private String owner;

        @Before
        public void setUp() throws Exception {
            or = new OwnerRepositoryImpl();
            file = new File("test.txt");
            file.mkdir();

            final ContentsAttribute attr = new ContentsAttribute();
            attr.setPath("src/test/resources/test.properties");
            final PropertiesService ps = new PropertiesServiceImpl(attr);

            owner = ps.getProperty("owner");
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Test
        public void ファイル所有者の変更ができて終了ステータスがtrueであること() throws Exception {
            final boolean status = or.setOwner("test.txt", owner);
            assertThat(status, is(true));
        }

        @Test
        public void 新しく設定するファイル所有者のユーザ名が現在設定されているファイル所有者のユーザ名と同一である場合に終了ステータスがfalseであること() throws Exception {
            or.setOwner("test.txt", owner);
            final boolean status = or.setOwner("test.txt", owner);

            assertThat(status, is(false));
        }

        @Test(expected = UserPrincipalNotFoundException.class)
        public void 新しく設定するファイル所有者として存在しないユーザの名前を指定した場合にUserPrincipalNotFoundExceptionが送出されること() throws Exception {
            try {
                or.setOwner("test.txt", "NotExist");
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }

        @Test(expected = FileNotFoundException.class)
        public void 存在しないファイルの所有者を変更しようとした場合にFileNotFoundExceptionが送出されること() throws Exception {
            try {
                or.setOwner("NotExist", owner);
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }
    }
}
