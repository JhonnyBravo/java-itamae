package java_itamae.domain.service.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java_itamae.domain.model.Attribute;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.service.properties.PropertiesService;
import java_itamae.domain.service.properties.PropertiesServiceImpl;

@RunWith(Enclosed.class)
public class FileServiceTest {
  public static class ファイルが存在しない場合 {
    private FileService fs;
    private PropertiesService ps;

    private Attribute attr;
    private File file;

    @Before
    public void setUp() throws Exception {
      final ContentsModel ca = new ContentsModel();
      ca.setPath("src/test/resources/test.properties");
      ps = new PropertiesServiceImpl(ca);

      fs = new FileServiceImpl();
      attr = new Attribute();
      attr.setPath("test.txt");

      file = new File("test.txt");
    }

    @After
    public void tearDown() throws Exception {
      if (file.isFile()) {
        file.delete();
      }
    }

    @Test
    public void ファイルを作成できて終了ステータスがtrueであること() throws Exception {
      final boolean status = fs.create(attr);
      assertThat(status, is(true));
      assertThat(file.isFile(), is(true));
    }

    @Test
    public void ファイル所有者を変更できて終了ステータスがtrueであること() throws Exception {
      attr.setOwner(ps.getProperty("owner"));
      final boolean status = fs.create(attr);
      assertThat(status, is(true));
    }

    @Test
    public void ファイルのグループ所有者を変更できて終了ステータスがtrueであること() throws Exception {
      attr.setGroup(ps.getProperty("group"));
      final boolean status = fs.create(attr);
      assertThat(status, is(true));
    }

    @Test
    public void ファイルのパーミッション設定を変更できて終了ステータスがtrueであること() throws Exception {
      attr.setMode("721");
      final boolean status = fs.create(attr);
      assertThat(status, is(true));
    }

    @Test
    public void delete実行時に何も実行されず終了ステータスがfalseであること() throws Exception {
      final boolean status = fs.delete(attr);
      assertThat(status, is(false));
      assertThat(file.isFile(), is(false));
    }
  }

  public static class ファイルが既に存在する場合1 {
    private FileService fs;
    private PropertiesService ps;
    private File file;

    @Before
    public void setUp() throws Exception {
      final ContentsModel ca = new ContentsModel();
      ca.setPath("src/test/resources/test.properties");
      ps = new PropertiesServiceImpl(ca);

      final Attribute attr = new Attribute();
      attr.setPath("test.txt");
      attr.setOwner(ps.getProperty("owner"));
      attr.setGroup(ps.getProperty("group"));
      attr.setMode("640");

      fs = new FileServiceImpl();
      fs.create(attr);

      file = new File("test.txt");
    }

    @After
    public void tearDown() throws Exception {
      if (file.isFile()) {
        file.delete();
      }
    }

    @Test
    public void パスに指定したファイルが既に存在する場合に終了ステータスがfalseであること() throws Exception {
      final Attribute attr = new Attribute();
      attr.setPath("test.txt");

      final boolean status = fs.create(attr);
      assertThat(status, is(false));
    }

    @Test
    public void 新しいファイル所有者として現在設定されているファイル所有者と同一のユーザ名を指定した場合に終了ステータスがfalseであること() throws Exception {
      final Attribute attr = new Attribute();
      attr.setPath("test.txt");
      attr.setOwner(ps.getProperty("owner"));

      final boolean status = fs.create(attr);
      assertThat(status, is(false));
    }

    @Test
    public void 新しいグループ所有者として現在設定されているグループ所有者と同一のグループ名を指定した場合に終了ステータスがfalseであること()
        throws Exception {
      final Attribute attr = new Attribute();
      attr.setPath("test.txt");
      attr.setGroup(ps.getProperty("group"));

      final boolean status = fs.create(attr);
      assertThat(status, is(false));
    }

    @Test
    public void 新しいパーミッションとして現在設定されているパーミッションと同一の値を指定した場合に終了ステータスがfalseであること() throws Exception {
      final Attribute attr = new Attribute();
      attr.setPath("test.txt");
      attr.setMode("640");

      final boolean status = fs.create(attr);
      assertThat(status, is(false));
    }

    @Test
    public void delete実行時にファイルを削除できて終了ステータスがtrueであること() throws Exception {
      final Attribute attr = new Attribute();
      attr.setPath("test.txt");

      final boolean status = fs.delete(attr);
      assertThat(status, is(true));
    }
  }

  public static class ファイルが既に存在する場合2 {
    private FileService fs;
    private PropertiesService ps;
    private File file;

    @Before
    public void setUp() throws Exception {
      final ContentsModel ca = new ContentsModel();
      ca.setPath("src/test/resources/test.properties");
      ps = new PropertiesServiceImpl(ca);

      final Attribute attr = new Attribute();
      attr.setPath("test.txt");

      fs = new FileServiceImpl();
      fs.create(attr);

      file = new File("test.txt");
    }

    @After
    public void tearDown() throws Exception {
      if (file.isFile()) {
        file.delete();
      }
    }

    @Test
    public void ファイル所有者を変更できて終了ステータスがtrueであること() throws Exception {
      final Attribute attr = new Attribute();
      attr.setPath("test.txt");
      attr.setOwner(ps.getProperty("owner"));

      final boolean status = fs.create(attr);
      assertThat(status, is(true));
    }

    @Test
    public void グループ所有者を変更できて終了ステータスがtrueであること() throws Exception {
      final Attribute attr = new Attribute();
      attr.setPath("test.txt");
      attr.setGroup(ps.getProperty("group"));

      final boolean status = fs.create(attr);
      assertThat(status, is(true));
    }

    @Test
    public void パーミッションを変更できて終了ステータスがtrueであること() throws Exception {
      final Attribute attr = new Attribute();
      attr.setPath("test.txt");
      attr.setMode("640");

      final boolean status = fs.create(attr);
      assertThat(status, is(true));
    }
  }

  public static class エラーテスト {
    private FileService fs;
    private PropertiesService ps;
    private File file;

    @Before
    public void setUp() throws Exception {
      fs = new FileServiceImpl();
      file = new File("test.txt");

      final ContentsModel attr = new ContentsModel();
      attr.setPath("src/test/resources/test.properties");
      ps = new PropertiesServiceImpl(attr);
    }

    @After
    public void tearDown() throws Exception {
      if (file.isFile()) {
        file.delete();
      }
    }

    @Test(expected = Exception.class)
    public void pathが指定されないままcreateを実行した場合にExceptionが送出されること() throws Exception {
      final Attribute attr = new Attribute();
      attr.setOwner(ps.getProperty("owner"));
      attr.setGroup(ps.getProperty("group"));
      attr.setMode("640");

      try {
        fs.create(attr);
      } catch (final Exception e) {
        System.err.println(e);
        throw e;
      }
    }

    @Test(expected = Exception.class)
    public void pathが指定されないままdeleteを実行した場合にExceptionが送出されること() throws Exception {
      final Attribute attr = new Attribute();

      try {
        fs.delete(attr);
      } catch (final Exception e) {
        System.err.println(e);
        throw e;
      }
    }

    @Test(expected = NoSuchFileException.class)
    public void 親ディレクトリが存在しない場合にNoSuchFileExceptionが送出されること() throws Exception {
      final Attribute attr = new Attribute();
      attr.setPath("NotExist/test.txt");
      attr.setOwner(ps.getProperty("owner"));
      attr.setGroup(ps.getProperty("group"));
      attr.setMode("640");

      try {
        fs.create(attr);
      } catch (final Exception e) {
        System.err.println(e);
        throw e;
      }
    }

    @Test(expected = UserPrincipalNotFoundException.class)
    public void 新しいファイル所有者として存在しないユーザ名を指定した場合にUserPrincipalNotFoundExceptionが送出されること()
        throws Exception {
      final Attribute attr = new Attribute();
      attr.setPath("test.txt");
      attr.setOwner("NotExist");
      attr.setGroup(ps.getProperty("group"));
      attr.setMode("640");

      try {
        fs.create(attr);
      } catch (final Exception e) {
        System.err.println(e);
        throw e;
      }
    }

    @Test(expected = UserPrincipalNotFoundException.class)
    public void 新しいグループ所有者として存在しないグループ名を指定した場合にUserPrincipalNotFoundExceptionが送出されること()
        throws Exception {
      final Attribute attr = new Attribute();
      attr.setPath("test.txt");
      attr.setOwner(ps.getProperty("owner"));
      attr.setGroup("NotExist");
      attr.setMode("640");

      try {
        fs.create(attr);
      } catch (final Exception e) {
        System.err.println(e);
        throw e;
      }
    }

    @Test(expected = Exception.class)
    public void 新しいパーミッションとして不正なパーミッション値を指定した場合にExceptionが送出されること() throws Exception {
      final Attribute attr = new Attribute();
      attr.setPath("test.txt");
      attr.setOwner(ps.getProperty("owner"));
      attr.setGroup(ps.getProperty("group"));
      attr.setMode("a40");

      try {
        fs.create(attr);
      } catch (final Exception e) {
        System.err.println(e);
        throw e;
      }
    }
  }
}
