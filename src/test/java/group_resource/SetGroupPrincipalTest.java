package group_resource;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.attribute.GroupPrincipal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link group_resource.SetGroupPrincipal} の単体テスト。
 */
public class SetGroupPrincipalTest {
    private SetGroupPrincipal sgp = new SetGroupPrincipal();
    private CreateGroupPrincipal cgp = new CreateGroupPrincipal();
    private GetGroupPrincipal ggp = new GetGroupPrincipal();
    private GetTestProperties gtp = new GetTestProperties();
    private String groupName;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        new File("test").mkdir();
        new File("test/test.txt").createNewFile();
        gtp.init("src/test/resources/test.properties");
        groupName = gtp.getGroupName();
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
     * {@link group_resource.CreateGroupPrincipal#runCommand()},
     * {@link group_resource.GetGroupPrincipal#runCommand()},
     * {@link group_resource.SetGroupPrincipal#runCommand()},
     * {@link group_resource.SetGroupPrincipal#getCode()} のためのテスト・メソッド。
     */
    @Test
    public void test1() {
        sgp.init("test/test.txt", groupName);
        sgp.runCommand();

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sgp.getCode());
            return;
        } else {
            // ファイルのグループ所有者を変更でき、終了ステータスが 2 であること。
            assertEquals(2, sgp.getCode());
        }

        cgp.init(groupName);
        GroupPrincipal expectPrincipal = cgp.runCommand();

        ggp.init("test/test.txt");
        GroupPrincipal actualPrincipal = ggp.runCommand();

        assertEquals(expectPrincipal, actualPrincipal);

        // 終了ステータスが 0 であること。
        sgp.runCommand();
        assertEquals(0, sgp.getCode());

    }

    @Test
    public void test2() {
        // ディレクトリのグループ所有者を変更でき、終了ステータスが 2 であること。
        sgp.init("test", groupName);
        sgp.runCommand();

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sgp.getCode());
            return;
        } else {
            // ディレクトリのグループ所有者を変更でき、終了ステータスが 2 であること。
            assertEquals(2, sgp.getCode());
        }

        cgp.init(groupName);
        GroupPrincipal expectPrincipal = cgp.runCommand();

        ggp.init("test");
        GroupPrincipal actualPrincipal = ggp.runCommand();

        assertEquals(expectPrincipal, actualPrincipal);

        // 終了ステータスが 0 であること。
        sgp.runCommand();
        assertEquals(0, sgp.getCode());
    }

    @Test
    public void test3() {
        // 存在しないグループを指定した場合にエラーとなり、終了ステータスが 1 であること。
        sgp.init("test", "NotExist");
        sgp.runCommand();

        assertEquals(1, sgp.getCode());
    }
}
