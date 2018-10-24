package owner_resource;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.attribute.UserPrincipal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * {@link owner_resource.SetUserPrincipal} の単体テスト。
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SetUserPrincipalTest {
    @Autowired
    private OwnerTestProperties otp;

    @Autowired
    private SetUserPrincipal sup;

    @Autowired
    private GetUserPrincipal gup;

    @Autowired
    private CreateUserPrincipal cup;

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
     * {@link owner_resource.CreateUserPrincipal#runCommand()},
     * {@link owner_resource.GetUserPrincipal#runCommand()},
     * {@link owner_resource.SetUserPrincipal#runCommand()},
     * {@link owner_resource.SetUserPrincipal#getCode()} のためのテスト・メソッド。
     */
    @Test
    public void test1() {
        // ファイルの所有者を変更でき、終了ステータスが 2 であること。
        String userName = otp.getOwnerName();

        sup.init("test/test.txt", userName);
        sup.runCommand();

        assertEquals(2, sup.getCode());

        cup.init(userName);
        UserPrincipal expectPrincipal = cup.runCommand();

        gup.init("test/test.txt");
        UserPrincipal actualPrincipal = gup.runCommand();

        assertEquals(expectPrincipal, actualPrincipal);

        // 終了ステータスが 0 であること。
        sup.runCommand();
        assertEquals(0, sup.getCode());
    }

    @Test
    public void test2() {
        // ディレクトリの所有者を変更でき、終了ステータスが 2 であること。
        String userName = otp.getOwnerName();

        sup.init("test", userName);
        sup.runCommand();

        assertEquals(2, sup.getCode());

        cup.init(userName);
        UserPrincipal expectPrincipal = cup.runCommand();

        gup.init("test");
        UserPrincipal actualPrincipal = gup.runCommand();

        assertEquals(expectPrincipal, actualPrincipal);

        // 終了ステータスが 0 であること。
        sup.runCommand();
        assertEquals(0, sup.getCode());
    }

    @Test
    public void test3() {
        // 存在しないユーザを指定した場合にエラーとなり、終了ステータスが 1 であること。
        sup.init("test", "NotExist");
        sup.runCommand();

        assertEquals(1, sup.getCode());
    }

}
