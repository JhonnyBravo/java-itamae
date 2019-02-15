package attribute_resource;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link attribute_resource.ModeResource} の単体テスト。
 */
public class ModeResourceTest {
    private File testDir = new File("test");
    private File testFile = new File("test/test.txt");
    private AttributeResource resource;

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
     * <p>
     * {@link attribute_resource.ModeResource#setAttribute()},
     * {@link attribute_resource.ModeResource#getCode()} のためのテスト・メソッド。
     * </p>
     * <p>
     * Others パーミッションの設定値を変更できることを確認する。
     * </p>
     */
    @Test
    public final void testOthers1() {
        resource = new ModeResource("test/test.txt", "001");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "001");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testOthers2() {
        resource = new ModeResource("test/test.txt", "002");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "002");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testOthers3() {
        resource = new ModeResource("test/test.txt", "003");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "003");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testOthers4() {
        resource = new ModeResource("test/test.txt", "004");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "004");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testOthers5() {
        resource = new ModeResource("test/test.txt", "005");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "005");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testOthers6() {
        resource = new ModeResource("test/test.txt", "006");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "006");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testOthers7() {
        resource = new ModeResource("test/test.txt", "007");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "007");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testOthers8() {
        // Others パーミッションに不正な値が指定されている場合にエラーとなり、終了コードが 1 であること。
        resource = new ModeResource("test", "008");
        resource.setAttribute();
        assertEquals(1, resource.getCode());
    }

    /**
     * Group パーミッションの設定値を変更できることを確認する。
     */
    @Test
    public final void testGroup1() {
        resource = new ModeResource("test/test.txt", "010");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "010");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testGroup2() {
        resource = new ModeResource("test/test.txt", "020");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "020");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testGroup3() {
        resource = new ModeResource("test/test.txt", "030");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "030");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testGroup4() {
        resource = new ModeResource("test/test.txt", "040");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "040");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testGroup5() {
        resource = new ModeResource("test/test.txt", "050");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "050");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testGroup6() {
        resource = new ModeResource("test/test.txt", "060");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "060");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testGroup7() {
        resource = new ModeResource("test/test.txt", "070");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "070");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testGroup8() {
        // Group パーミッションに不正な値が指定されている場合にエラーとなり、終了コードが 1 であること。
        resource = new ModeResource("test", "080");
        resource.setAttribute();
        assertEquals(1, resource.getCode());
    }

    /**
     * Owner パーミッションの設定値を変更できることを確認する。
     */
    @Test
    public final void testOwner1() {
        resource = new ModeResource("test/test.txt", "100");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "100");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testOwner2() {
        resource = new ModeResource("test/test.txt", "200");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "200");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testOwner3() {
        resource = new ModeResource("test/test.txt", "300");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "300");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testOwner4() {
        resource = new ModeResource("test/test.txt", "400");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "400");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testOwner5() {
        resource = new ModeResource("test/test.txt", "500");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "500");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testOwner6() {
        resource = new ModeResource("test/test.txt", "600");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "600");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testOwner7() {
        resource = new ModeResource("test/test.txt", "700");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "700");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void testOwner8() {
        // Owner パーミッションに不正な値が指定されている場合にエラーとなり、終了コードが 1 であること。
        resource = new ModeResource("test", "800");
        resource.setAttribute();
        assertEquals(1, resource.getCode());
    }

    /**
     * Owner パーミッション, Group パーミッション, Others パーミッションの設定値を同時に変更できることを確認する。
     */
    @Test
    public final void testAll1() {
        resource = new ModeResource("test/test.txt", "740");
        resource.setAttribute();

        if (this.isWindows()) {
            // OS が Windows である場合にエラーとなり、終了コードが 1 であること。
            assertEquals(1, resource.getCode());
            return;
        }

        // ファイルのパーミッション設定を変更でき、終了コードが 2 であること。
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());

        // ディレクトリのパーミッション設定を変更でき、終了コードが 2 であること。
        resource = new ModeResource("test", "740");
        resource.setAttribute();
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.setAttribute();
        assertEquals(0, resource.getCode());
    }

    /**
     * パーミッションの設定値に不正な値が指定されている場合にエラーとなることを確認する。
     */
    @Test
    public final void testError1() {
        // 不正なパーミッション値が指定されている場合にエラーとなり、終了コードが 1 であること。
        resource = new ModeResource("test/test.txt", "7a0");
        resource.setAttribute();
        assertEquals(1, resource.getCode());
    }

    @Test
    public final void testError2() {
        // 不正なパーミッション値が指定されている場合にエラーとなり、終了コードが 1 であること。
        resource = new ModeResource("test/test.txt", "+66");
        resource.setAttribute();
        assertEquals(1, resource.getCode());
    }

    @Test
    public final void testError3() {
        // 不正なパーミッション値が指定されている場合にエラーとなり、終了コードが 1 であること。
        resource = new ModeResource("test/test.txt", "-70");
        resource.setAttribute();
        assertEquals(1, resource.getCode());
    }

    @Test
    public final void testError4() {
        // 不正なパーミッション値が指定されている場合にエラーとなり、終了コードが 1 であること。
        resource = new ModeResource("test/test.txt", "10");
        resource.setAttribute();
        assertEquals(1, resource.getCode());
    }

    @Test
    public final void testError5() {
        // 不正なパーミッション値が指定されている場合にエラーとなり、終了コードが 1 であること。
        resource = new ModeResource("test/test.txt", "7670");
        resource.setAttribute();
        assertEquals(1, resource.getCode());
    }

}
