package basic_action_resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

/**
 * @author sanfr
 *
 */
public class FileResourceTest {
    private ActionResource resource;
    private File file;

    /**
     * {@link basic_action_resource.FileResource#create()},
     * {@link basic_action_resource.FileResource#delete()} のためのテスト・メソッド。
     */
    @Test
    public final void test1() {
        // ファイルを新規作成でき、終了コードが 2 であること。
        file = new File("test.txt");
        resource = new FileResource("test.txt");
        resource.create();

        assertTrue(file.isFile());
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.create();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void test2() {
        // ファイルを削除でき、終了コードが 2 であること。
        file = new File("test.txt");
        resource = new FileResource("test.txt");
        resource.delete();

        assertFalse(file.isFile());
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.delete();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void test3() {
        // エラーが発生した場合に終了コードが 1 であること。
        file = new File("NotExist/test.txt");
        resource = new FileResource("NotExist/test.txt");
        resource.create();

        assertFalse(file.isFile());
        assertEquals(1, resource.getCode());
    }

}
