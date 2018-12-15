package owner_resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.nio.file.attribute.UserPrincipal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link owner_resource.Owner} の単体テスト。
 */
public class OwnerTest {
    private GetTestProperties gtp = new GetTestProperties();
    private String ownerName;

    private Owner resource = new Owner();
    private File testDir = new File("test");
    private File testFile = new File("test/test.txt");

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        testDir.mkdir();
        testFile.createNewFile();
        ownerName = gtp.getOwnerName("src/test/resources/test.properties");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        testFile.delete();
        testDir.delete();
    }

    /**
     * ファイルの所有者を変更できるかどうかを確認する。
     */
    @Test
    public void test1() {
        // 所有者を変更でき、終了コードが 2 であること。
        UserPrincipal curUp = resource.getOwner("test/test.txt");
        UserPrincipal newUp = resource.createOwner(ownerName);
        assertFalse(curUp.equals(newUp));

        resource.setOwner("test/test.txt", ownerName);
        assertEquals(2, resource.getCode());

        curUp = resource.getOwner("test/test.txt");
        newUp = resource.createOwner(ownerName);
        assertEquals(newUp, curUp);

        // 終了コードが 0 であること。
        resource.setOwner("test/test.txt", ownerName);
        assertEquals(0, resource.getCode());
    }

    /**
     * ディレクトリの所有者を変更できるかどうかを確認する。
     */
    @Test
    public void test2() {
        // 所有者を変更でき、終了コードが 2 であること。
        UserPrincipal curUp = resource.getOwner("test");
        UserPrincipal newUp = resource.createOwner(ownerName);
        assertFalse(curUp.equals(newUp));

        resource.setOwner("test", ownerName);
        assertEquals(2, resource.getCode());

        curUp = resource.getOwner("test");
        newUp = resource.createOwner(ownerName);
        assertEquals(newUp, curUp);

        // 終了コードが 0 であること。
        resource.setOwner("test", ownerName);
        assertEquals(0, resource.getCode());
    }

    /**
     * 存在しないユーザを指定した場合にエラーとなることを確認する。
     */
    @Test
    public void test3() {
        // 終了ステータスが 1 であること。
        resource.setOwner("test", "NotExist");
        assertEquals(1, resource.getCode());
    }
}
