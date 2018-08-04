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

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        new File("test").mkdir();
        new File("test/test.txt").createNewFile();
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
        String groupName = new GetGroupNameFromProperties("src/test/resources/test.properties").getGroupName();

        SetGroupPrincipal sgp = new SetGroupPrincipal("test/test.txt", groupName);
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

        CreateGroupPrincipal cgp = new CreateGroupPrincipal(groupName);
        GroupPrincipal expectPrincipal = cgp.runCommand();

        GetGroupPrincipal ggp = new GetGroupPrincipal("test/test.txt");
        GroupPrincipal actualPrincipal = ggp.runCommand();

        assertEquals(expectPrincipal, actualPrincipal);

        // 終了ステータスが 0 であること。
        sgp.runCommand();
        assertEquals(0, sgp.getCode());

    }

    @Test
    public void test2() {
        // ディレクトリのグループ所有者を変更でき、終了ステータスが 2 であること。
        String groupName = new GetGroupNameFromProperties("src/test/resources/test.properties").getGroupName();

        SetGroupPrincipal sgp = new SetGroupPrincipal("test", groupName);
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

        CreateGroupPrincipal cgp = new CreateGroupPrincipal(groupName);
        GroupPrincipal expectPrincipal = cgp.runCommand();

        GetGroupPrincipal ggp = new GetGroupPrincipal("test");
        GroupPrincipal actualPrincipal = ggp.runCommand();

        assertEquals(expectPrincipal, actualPrincipal);

        // 終了ステータスが 0 であること。
        sgp.runCommand();
        assertEquals(0, sgp.getCode());
    }

    @Test
    public void test3() {
        // 存在しないグループを指定した場合にエラーとなり、終了ステータスが 1 であること。
        SetGroupPrincipal sgp = new SetGroupPrincipal("test", "NotExist");
        sgp.runCommand();

        assertEquals(1, sgp.getCode());
    }

}
