package mode_resource;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * {@link mode_resource.SetPermission} の単体テスト。
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SetPermissionTest {
    @Autowired
    private SetPermission sp;

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
     * <p>
     * {@link mode_resource.CreatePermission#runCommand()},
     * {@link mode_resource.GetPermission#runCommand()},
     * {@link mode_resource.SetPermission#runCommand()},
     * {@link mode_resource.SetPermission#getCode()} のためのテスト・メソッド。
     * </p>
     * 
     * <p>
     * 確認事項
     * </p>
     * 
     * <ul>
     * <li>user パーミッションを変更できること。</li>
     * <li>終了ステータスが正しく設定されること。</li>
     * </ul>
     */
    @Test
    public void test1_1() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "000");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "000");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    @Test
    public void test1_2() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "100");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "100");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    @Test
    public void test1_3() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "200");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "200");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    @Test
    public void test1_4() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "300");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "300");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    @Test
    public void test1_5() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "400");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "400");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    @Test
    public void test1_6() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "500");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "500");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    @Test
    public void test1_7() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "600");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "600");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    @Test
    public void test1_8() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "700");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "700");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    /**
     * <p>
     * 確認事項
     * </p>
     * 
     * <ul>
     * <li>不正な user パーミッション値が設定されている場合にエラーとなること。</li>
     * <li>終了ステータスが正しく設定されること。</li>
     * </ul>
     */
    @Test
    public void test1_9() {
        sp.init("test/test.txt", "800");
        sp.runCommand();
        assertEquals(1, sp.getCode());

        sp.init("test", "800");
        sp.runCommand();
        assertEquals(1, sp.getCode());
    }

    /**
     * <p>
     * 確認事項
     * </p>
     * 
     * <ul>
     * <li>group パーミッションを変更できること。</li>
     * <li>終了ステータスが正しく設定されること。</li>
     * </ul>
     */
    @Test
    public void test2_1() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "010");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "010");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    @Test
    public void test2_2() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "020");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "020");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    @Test
    public void test2_3() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "030");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "030");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    @Test
    public void test2_4() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "040");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "040");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    @Test
    public void test2_5() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "050");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "050");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    @Test
    public void test2_6() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "060");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "060");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    @Test
    public void test2_7() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "070");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "070");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    /**
     * <p>
     * 確認事項
     * </p>
     * 
     * <ul>
     * <li>不正な group パーミッション値が設定されている場合にエラーとなること。</li>
     * <li>終了ステータスが正しく設定されること。</li>
     * </ul>
     */
    @Test
    public void test2_8() {
        sp.init("test/test.txt", "080");
        sp.runCommand();
        assertEquals(1, sp.getCode());

        sp.init("test", "080");
        sp.runCommand();
        assertEquals(1, sp.getCode());
    }

    /**
     * <p>
     * 確認事項
     * </p>
     * 
     * <ul>
     * <li>others パーミッションを変更できること。</li>
     * <li>終了ステータスが正しく設定されること。</li>
     * </ul>
     */
    @Test
    public void test3_1() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "001");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "001");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    @Test
    public void test3_2() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "002");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "002");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    @Test
    public void test3_3() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "003");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "003");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    @Test
    public void test3_4() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "004");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "004");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    @Test
    public void test3_5() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "005");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "005");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    @Test
    public void test3_6() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "006");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "006");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    @Test
    public void test3_7() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "007");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "007");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    /**
     * <p>
     * 確認事項
     * </p>
     * 
     * <ul>
     * <li>不正な others パーミッション値が設定されている場合にエラーとなること。</li>
     * <li>終了ステータスが正しく設定されること。</li>
     * </ul>
     */
    @Test
    public void test3_8() {
        sp.init("test/test.txt", "008");
        sp.runCommand();
        assertEquals(1, sp.getCode());

        sp.init("test", "008");
        sp.runCommand();
        assertEquals(1, sp.getCode());
    }

    /**
     * <p>
     * 確認事項
     * </p>
     * 
     * <ul>
     * <li>user パーミッション, group パーミッション, others パーミッションを同時に変更できること。</li>
     * <li>終了ステータスが正しく設定されること。</li>
     * </ul>
     */
    @Test
    public void test4_1() {
        String osName = System.getProperty("os.name");

        sp.init("test/test.txt", "640");
        sp.runCommand();

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, sp.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, sp.getCode());
        }

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        sp.init("test", "740");
        sp.runCommand();
        assertEquals(2, sp.getCode());

        // 終了ステータスが 0 であること。
        sp.runCommand();
        assertEquals(0, sp.getCode());

        // 終了処理
        sp.init("test", "700");
        sp.runCommand();

        sp.init("test/test.txt", "700");
        sp.runCommand();
    }

    /**
     * <p>
     * 確認事項
     * </p>
     * 
     * <ul>
     * <li>不正なパーミッション値が設定されている場合にエラーとなること。</li>
     * <li>終了ステータスが正しく設定されること。</li>
     * </ul>
     */
    @Test
    public void test5_1() {
        sp.init("test/test.txt", "a66");
        sp.runCommand();
        assertEquals(1, sp.getCode());

        sp.init("test", "7a0");
        sp.runCommand();
        assertEquals(1, sp.getCode());
    }

    @Test
    public void test5_2() {
        sp.init("test/test.txt", "+66");
        sp.runCommand();
        assertEquals(1, sp.getCode());

        sp.init("test/test.txt", "7766");
        sp.runCommand();
        assertEquals(1, sp.getCode());

        sp.init("test", "-70");
        sp.runCommand();
        assertEquals(1, sp.getCode());

        sp.init("test", "7670");
        sp.runCommand();
        assertEquals(1, sp.getCode());
    }
}
