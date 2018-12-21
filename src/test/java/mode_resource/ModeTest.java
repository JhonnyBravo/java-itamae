package mode_resource;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link mode_resource.Mode} の単体テスト。
 */
public class ModeTest {
    private Mode resource = new Mode();

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
     * {@link mode_resource.Mode#setMode(String, String)},
     * {@link mode_resource.Mode#getCode()} のためのテスト・メソッド。
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
        resource.setMode("test/test.txt", "000");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "000");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "000");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "000");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
    }

    @Test
    public void test1_2() {
        resource.setMode("test/test.txt", "100");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "100");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "100");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "100");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
    }

    @Test
    public void test1_3() {
        resource.setMode("test/test.txt", "200");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "200");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "200");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "200");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
    }

    @Test
    public void test1_4() {
        resource.setMode("test/test.txt", "300");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "300");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "300");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "300");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
    }

    @Test
    public void test1_5() {
        resource.setMode("test/test.txt", "400");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "400");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "400");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "400");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
    }

    @Test
    public void test1_6() {
        resource.setMode("test/test.txt", "500");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "500");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "500");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "500");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
    }

    @Test
    public void test1_7() {
        resource.setMode("test/test.txt", "600");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "600");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "600");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "600");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
    }

    @Test
    public void test1_8() {
        resource.setMode("test/test.txt", "700");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "700");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "700");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "700");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
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
        resource.setMode("test/test.txt", "800");
        assertEquals(1, resource.getCode());

        resource.setMode("test", "800");
        assertEquals(1, resource.getCode());
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
        resource.setMode("test/test.txt", "010");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "010");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "010");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "010");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
    }

    @Test
    public void test2_2() {
        resource.setMode("test/test.txt", "020");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "020");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "020");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "020");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
    }

    @Test
    public void test2_3() {
        resource.setMode("test/test.txt", "030");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "030");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "030");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "030");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
    }

    @Test
    public void test2_4() {
        resource.setMode("test/test.txt", "040");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "040");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "040");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "040");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
    }

    @Test
    public void test2_5() {
        resource.setMode("test/test.txt", "050");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "050");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "050");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "050");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
    }

    @Test
    public void test2_6() {
        resource.setMode("test/test.txt", "060");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "060");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "060");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "060");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
    }

    @Test
    public void test2_7() {
        resource.setMode("test/test.txt", "070");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "070");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "070");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "070");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
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
        resource.setMode("test/test.txt", "080");
        assertEquals(1, resource.getCode());

        resource.setMode("test", "080");
        assertEquals(1, resource.getCode());
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
        resource.setMode("test/test.txt", "001");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "001");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "001");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "001");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
    }

    @Test
    public void test3_2() {
        resource.setMode("test/test.txt", "002");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "002");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "002");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "002");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
    }

    @Test
    public void test3_3() {
        resource.setMode("test/test.txt", "003");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "003");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "003");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "003");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
    }

    @Test
    public void test3_4() {
        resource.setMode("test/test.txt", "004");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "004");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "004");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "004");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
    }

    @Test
    public void test3_5() {
        resource.setMode("test/test.txt", "005");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "005");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "005");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "005");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
    }

    @Test
    public void test3_6() {
        resource.setMode("test/test.txt", "006");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "006");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "006");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "006");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
    }

    @Test
    public void test3_7() {
        resource.setMode("test/test.txt", "007");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "007");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "007");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "007");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
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
        resource.setMode("test/test.txt", "008");
        assertEquals(1, resource.getCode());

        resource.setMode("test", "008");
        assertEquals(1, resource.getCode());
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
        resource.setMode("test/test.txt", "640");

        String osName = System.getProperty("os.name");

        if (osName.substring(0, 3).equals("Win")) {
            // OS が Windows である場合にエラーとなり、終了ステータスが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        } else {
            // ファイルのパーミッションを変更でき、終了ステータスが 2 であること。
            assertEquals(2, resource.getCode());
        }

        // 終了ステータスが 0 であること。
        resource.setMode("test/test.txt", "640");
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッションを変更でき、終了ステータスが 2 であること。
        resource.setMode("test", "740");
        assertEquals(2, resource.getCode());

        // 終了ステータスが 0 であること。
        resource.setMode("test", "740");
        assertEquals(0, resource.getCode());

        // 終了処理
        resource.setMode("test", "700");
        resource.setMode("test/test.txt", "700");
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
        resource.setMode("test/test.txt", "a66");
        assertEquals(1, resource.getCode());

        resource.setMode("test", "7a0");
        assertEquals(1, resource.getCode());
    }

    @Test
    public void test5_2() {
        resource.setMode("test/test.txt", "+66");
        assertEquals(1, resource.getCode());

        resource.setMode("test/test.txt", "7766");
        assertEquals(1, resource.getCode());

        resource.setMode("test", "-70");
        assertEquals(1, resource.getCode());

        resource.setMode("test", "7670");
        assertEquals(1, resource.getCode());
    }
}
