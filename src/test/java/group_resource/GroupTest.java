package group_resource;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.attribute.GroupPrincipal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link group_resource.Group} の単体テスト。
 */
public class GroupTest {
    private Group resource = new Group();
    private GetTestProperties gtp = new GetTestProperties();
    private String groupName;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        new File("test").mkdir();
        new File("test/test.txt").createNewFile();
        groupName = gtp.getGroupName("src/test/resources/test.properties");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        new File("test/test.txt").delete();
        new File("test").delete();
    }

    /**
     * {@link group_resource.Group#setGroup(String, String)},
     * {@link group_resource.Group#getCode()} のためのテスト・メソッド。
     */
    @Test
    public void test1() {
        resource.setGroup("test/test.txt", groupName);

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのグループ所有者を変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        GroupPrincipal expectPrincipal = resource.createGroup(groupName);
        GroupPrincipal actualPrincipal = resource.getGroup("test/test.txt");

        assertEquals(expectPrincipal, actualPrincipal);

        // 終了ステータスが 0 であること。
        resource.setGroup("test/test.txt", groupName);
        assertEquals(0, resource.getCode());
    }

    @Test
    public void test2() {
        // ディレクトリのグループ所有者を変更でき、終了ステータスが 2 であること。
        resource.setGroup("test", groupName);

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ディレクトリのグループ所有者を変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        GroupPrincipal expectPrincipal = resource.createGroup(groupName);
        GroupPrincipal actualPrincipal = resource.getGroup("test");
        assertEquals(expectPrincipal, actualPrincipal);

        // 終了ステータスが 0 であること。
        resource.setGroup("test", groupName);
        assertEquals(0, resource.getCode());
    }

    @Test
    public void test3() {
        // 存在しないグループを指定した場合にエラーとなり、終了ステータスが 1 であること。
        resource.setGroup("test", "NotExist");
        assertEquals(1, resource.getCode());
    }
}
