package java_itamae.domain.service.directory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java_itamae.domain.model.Attribute;
import property_resource.PropertyResource;

@RunWith(Enclosed.class)
public class DirectoryServiceTest {
    public static class エラーテスト {
        private DirectoryService ds;
        private File file;
        private Map<String, String> props;

        @Before
        public void setUp() throws Exception {
            ds = new DirectoryServiceImpl();
            file = new File("test_dir");

            final PropertyResource pr = new PropertyResource("src/test/resources/test.properties");
            props = pr.getContent();
        }

        @After
        public void tearDown() throws Exception {
            if (file.isDirectory()) {
                file.delete();
            }
        }

        @Test(expected = Exception.class)
        public void pathが指定されないままcreateを実行した場合にExceptionが送出されること() throws Exception {
            final Attribute attr = new Attribute();
            attr.setOwner(props.get("owner"));
            attr.setGroup(props.get("group"));
            attr.setMode("640");

            try {
                ds.create(attr);
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }

        @Test(expected = Exception.class)
        public void pathが指定されないままdeleteを実行した場合にExceptionが送出されること() throws Exception {
            final Attribute attr = new Attribute();

            try {
                ds.delete(attr);
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }

        @Test(expected = UserPrincipalNotFoundException.class)
        public void 新しいディレクトリ所有者として存在しないユーザ名を指定した場合にUserPrincipalNotFoundExceptionが送出されること() throws Exception {
            final Attribute attr = new Attribute();
            attr.setPath("test_dir");
            attr.setOwner("NotExist");
            attr.setGroup(props.get("group"));
            attr.setMode("640");

            try {
                ds.create(attr);
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }

        @Test(expected = UserPrincipalNotFoundException.class)
        public void 新しいグループ所有者として存在しないグループ名を指定した場合にUserPrincipalNotFoundExceptionが送出されること() throws Exception {
            final Attribute attr = new Attribute();
            attr.setPath("test_dir");
            attr.setOwner(props.get("owner"));
            attr.setGroup("NotExist");
            attr.setMode("640");

            try {
                ds.create(attr);
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }

        @Test(expected = Exception.class)
        public void 新しいパーミッションとして不正なパーミッション値を指定した場合にExceptionが送出されること() throws Exception {
            final Attribute attr = new Attribute();
            attr.setPath("test_dir");
            attr.setOwner(props.get("owner"));
            attr.setGroup(props.get("group"));
            attr.setMode("a40");

            try {
                ds.create(attr);
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }
    }

    public static class ディレクトリが既に存在する場合1 {
        private DirectoryService ds;

        private File file;
        private Map<String, String> props;

        @Before
        public void setUp() throws Exception {
            final PropertyResource pr = new PropertyResource("src/test/resources/test.properties");
            props = pr.getContent();
            file = new File("test_dir");

            final Attribute attr = new Attribute();
            attr.setPath("test_dir");
            attr.setOwner(props.get("owner"));
            attr.setGroup(props.get("group"));
            attr.setMode("640");

            ds = new DirectoryServiceImpl();
            ds.create(attr);
        }

        @After
        public void tearDown() throws Exception {
            if (file.isDirectory()) {
                file.delete();
            }
        }

        @Test
        public void パスに指定したディレクトリが既に存在する場合に終了ステータスがfalseであること() throws Exception {
            final Attribute attr = new Attribute();
            attr.setPath("test_dir");

            final boolean status = ds.create(attr);
            assertThat(status, is(false));
        }

        @Test
        public void 新しいディレクトリ所有者として現在設定されているディレクトリ所有者と同一のユーザ名を指定した場合に終了ステータスがfalseであること() throws Exception {
            final Attribute attr = new Attribute();
            attr.setPath("test_dir");
            attr.setOwner(props.get("owner"));

            final boolean status = ds.create(attr);
            assertThat(status, is(false));
        }

        @Test
        public void 新しいグループ所有者として現在設定されているグループ所有者と同一のグループ名を指定した場合に終了ステータスがfalseであること() throws Exception {
            final Attribute attr = new Attribute();
            attr.setPath("test_dir");
            attr.setGroup(props.get("group"));

            final boolean status = ds.create(attr);
            assertThat(status, is(false));
        }

        @Test
        public void 新しいパーミッションとして現在設定されているパーミッションと同一の値を指定した場合に終了ステータスがfalseであること() throws Exception {
            final Attribute attr = new Attribute();
            attr.setPath("test_dir");
            attr.setMode("640");

            final boolean status = ds.create(attr);
            assertThat(status, is(false));
        }
    }

    public static class ディレクトリが既に存在する場合2 {
        private DirectoryService ds;
        private Map<String, String> props;
        private File directory;

        @Before
        public void setUp() throws Exception {
            directory = new File("test_dir");
            directory.mkdir();

            final PropertyResource pr = new PropertyResource("src/test/resources/test.properties");
            props = pr.getContent();

            ds = new DirectoryServiceImpl();
        }

        @After
        public void tearDown() throws Exception {
            if (directory.isDirectory()) {
                directory.delete();
            }
        }

        @Test
        public void ディレクトリ所有者の変更ができて終了ステータスがtrueであること() throws Exception {
            final Attribute attr = new Attribute();
            attr.setPath("test_dir");
            attr.setOwner(props.get("owner"));

            final boolean status = ds.create(attr);
            assertThat(status, is(true));
        }

        @Test
        public void グループ所有者の変更ができて終了ステータスがtrueであること() throws Exception {
            final Attribute attr = new Attribute();
            attr.setPath("test_dir");
            attr.setGroup(props.get("group"));

            final boolean status = ds.create(attr);
            assertThat(status, is(true));
        }

        @Test
        public void パーミッション設定の変更ができて終了ステータスがtrueであること() throws Exception {
            final Attribute attr = new Attribute();
            attr.setPath("test_dir");
            attr.setMode("741");

            final boolean status = ds.create(attr);
            assertThat(status, is(true));
        }
    }

    public static class ディレクトリが存在しない場合 {
        private DirectoryService ds;
        private Map<String, String> props;
        private File directory;

        @Before
        public void setUp() throws Exception {
            ds = new DirectoryServiceImpl();
            directory = new File("test_dir");
            final PropertyResource pr = new PropertyResource("src/test/resources/test.properties");
            props = pr.getContent();
        }

        @After
        public void tearDown() throws Exception {
            if (directory.isDirectory()) {
                directory.delete();
            }
        }

        @Test
        public void ディレクトリを作成できて終了ステータスがtrueであること() throws Exception {
            final Attribute attr = new Attribute();
            attr.setPath("test_dir");

            final boolean status = ds.create(attr);
            assertThat(status, is(true));
            assertThat(directory.isDirectory(), is(true));
        }

        @Test
        public void ディレクトリの所有者を変更できて終了ステータスがtrueであること() throws Exception {
            final Attribute attr = new Attribute();
            attr.setPath("test_dir");
            attr.setOwner(props.get("owner"));

            final boolean status = ds.create(attr);
            assertThat(status, is(true));
        }

        @Test
        public void ディレクトリのグループ所有者を変更できて終了ステータスがtrueであること() throws Exception {
            final Attribute attr = new Attribute();
            attr.setPath("test_dir");
            attr.setGroup(props.get("group"));

            final boolean status = ds.create(attr);
            assertThat(status, is(true));
        }

        @Test
        public void ディレクトリのパーミッション設定を変更できて終了ステータスがtrueであること() throws Exception {
            final Attribute attr = new Attribute();
            attr.setPath("test_dir");
            attr.setMode("741");

            final boolean status = ds.create(attr);
            assertThat(status, is(true));
        }
    }

    public static class 親ディレクトリが存在しない場合 {
        private DirectoryService ds;
        private Attribute attr;

        @Before
        public void setUp() throws Exception {
            ds = new DirectoryServiceImpl();
            attr = new Attribute();
            attr.setPath("parent/sub1/sub2");
        }

        @After
        public void tearDown() throws Exception {
            attr.setPath("parent");
            ds.setRecursive(true);
            ds.delete(attr);
        }

        @Test(expected = NoSuchFileException.class)
        public void setRecursiveを指定しない場合にNoSuchFileExceptionが送出されること() throws Exception {
            try {
                ds.create(attr);
            } catch (final Exception e) {
                System.err.println(e);
                final File parent = new File("parent");
                assertThat(parent.isDirectory(), is(false));
                throw e;
            }
        }

        @Test
        public void setRecursiveをtrueに指定した場合に親ディレクトリも含めてディレクトリが作成されて終了ステータスがtrueであること() throws Exception {
            ds.setRecursive(true);
            final boolean status = ds.create(attr);
            assertThat(status, is(true));

            final File parent = new File("parent");
            final File sub1 = new File("parent/sub1");
            final File sub2 = new File("parent/sub1/sub2");

            assertThat(parent.isDirectory(), is(true));
            assertThat(sub1.isDirectory(), is(true));
            assertThat(sub2.isDirectory(), is(true));
        }
    }

    public static class ディレクトリを削除する場合 {
        private DirectoryService ds;

        @Before
        public void setUp() throws Exception {
            ds = new DirectoryServiceImpl();
        }

        @Test
        public void 削除対象とするディレクトリが存在しない状態でsetRecursiveを指定せずにdeleteを実行した場合に終了ステータスがfalseであること() throws Exception {
            final Attribute attr = new Attribute();
            attr.setPath("test_dir");

            final boolean status = ds.delete(attr);
            assertThat(status, is(false));
        }

        @Test
        public void 削除対象とするディレクトリが存在しない状態でsetRecursiveにtrueを指定してdeleteを実行した場合に終了ステータスがfalseであること() throws Exception {
            final Attribute attr = new Attribute();
            attr.setPath("test_dir");

            ds.setRecursive(true);
            final boolean status = ds.delete(attr);

            assertThat(status, is(false));
        }

        @Test
        public void 削除対象とするディレクトリが存在する状態でsetRecursiveを指定せずにdeleteを実行した場合にディレクトリを削除できて終了ステータスがtrueであること()
                throws Exception {
            final File directory = new File("test_dir");
            directory.mkdir();

            final Attribute attr = new Attribute();
            attr.setPath("test_dir");

            final boolean status = ds.delete(attr);
            assertThat(status, is(true));
            assertThat(directory.isDirectory(), is(false));
        }

        @Test
        public void 削除対象とするディレクトリが存在する状態でsetRecursiveにtrueを指定してdeleteを実行した場合にディレクトリを一括削除できて終了ステータスがtrueであること()
                throws Exception {
            final File directory = new File("parent/sub1/sub2");
            directory.mkdirs();

            final Attribute attr = new Attribute();
            attr.setPath("parent");

            ds.setRecursive(true);
            final boolean status = ds.delete(attr);

            assertThat(status, is(true));
            final File parent = new File("parent");
            assertThat(parent.isDirectory(), is(false));
        }
    }
}
