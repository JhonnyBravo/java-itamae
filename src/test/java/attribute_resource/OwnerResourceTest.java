package attribute_resource;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import properties_resource.PropertiesController;

/**
 * {@link attribute_resource.OwnerResource} の単体テスト。
 */
public class OwnerResourceTest {
    private File testDir = new File("test");
    private File testFile = new File("test/test.txt");
    private String ownerName = this.getOwnerName();
    private AttributeResource resource;

    /**
     * @return ownerName テスト用プロパティファイルから設定値を取得する。
     */
    private String getOwnerName() {
        PropertiesController pc = new PropertiesController();
        pc.setPath("src/test/resources/test.properties");
        Properties p = pc.getProperties();
        String ownerName = p.getProperty("ownerName");
        return ownerName;
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        testDir.mkdir();
        testFile.createNewFile();
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
     * {@link attribute_resource.OwnerResource#setAttribute()},
     * {@link attribute_resource.OwnerResource#getCode()} のためのテスト・メソッド。
     */
    @Test
    public final void test1() {
        // ファイルの所有者を変更でき、終了コードが 2 であること。
        resource = new OwnerResource("test/test.txt", ownerName);
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void test2() {
        // ディレクトリの所有者を変更でき、終了コードが 2 であること。
        resource = new OwnerResource("test", ownerName);
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void test3() {
        // 存在しないユーザを指定した場合にエラーとなり、終了コードが 1 であること。
        resource = new OwnerResource("test", "NotExist");
        resource.setAttribute();
        assertEquals(1, resource.getCode());
    }

    @Test
    public final void test4() {
        // 存在しないパスを指定した場合にエラーとなり、終了コードが 1 であること。
        resource = new OwnerResource("NotExist", ownerName);
        resource.setAttribute();
        assertEquals(1, resource.getCode());
    }

}
