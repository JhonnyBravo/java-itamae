package attribute_resource;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import properties_resource.PropertiesController;

/**
 * {@link attribute_resource.GroupResource} の単体テスト。
 */
public class GroupResourceTest {
    private File testDir = new File("test");
    private File testFile = new File("test/test.txt");

    private String groupName = this.getGroupName();
    private AttributeResource resource;

    /**
     * @return groupName テスト用プロパティファイルから設定値を取得する。
     */
    private String getGroupName() {
        PropertiesController pc = new PropertiesController();
        pc.setPath("src/test/resources/test.properties");
        Properties p = pc.getProperties();
        String groupName = p.getProperty("groupName");
        return groupName;
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
     * {@link attribute_resource.GroupResource#setAttribute()},
     * {@link attribute_resource.GroupResource#getCode()} のためのテスト・メソッド。
     */
    @Test
    public final void test1() {
        resource = new GroupResource("test/test.txt", groupName);
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // グループ所有者を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void test2() {
        resource = new GroupResource("test", groupName);
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ディレクトリの所有者を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void test3() {
        // 存在しないグループを指定した場合にエラーとなり、終了コードが 1 であること。
        resource = new GroupResource("test", "NotExist");
        resource.setAttribute();
        assertEquals(1, resource.getCode());
    }

    @Test
    public final void test4() {
        // 存在しないパスを指定した場合にエラーとなり、終了コードが 1 であること。
        resource = new GroupResource("NotExist", groupName);
        resource.setAttribute();
        assertEquals(1, resource.getCode());
    }

}
