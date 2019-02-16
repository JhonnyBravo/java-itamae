package basic_action_resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

/**
 * {@link basic_action_resource.DirectoryResource} の単体テスト。
 */
public class DirectoryResourceTest {
    private ActionResource resource;
    private File file;

    /**
     * {@link basic_action_resource.DirectoryResource#create()},
     * {@link basic_action_resource.DirectoryResource#delete()} のためのテスト・メソッド。
     */
    @Test
    public final void test1() {
        // ディレクトリを新規作成でき、終了コードが 2 であること。
        file = new File("test");
        resource = new DirectoryResource("test");
        resource.create();

        assertTrue(file.isDirectory());
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.create();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void test2() {
        // ディレクトリを削除でき、終了コードが 2 であること。
        file = new File("test");
        resource = new DirectoryResource("test");
        resource.delete();

        assertFalse(file.isDirectory());
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.delete();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void test3() {
        // ディレクトリを新規作成でき、終了コードが 2 であること。
        file = new File("test/sub1/sub2");
        resource = new DirectoryResource("test/sub1/sub2");
        resource.create();

        assertTrue(file.isDirectory());
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.create();
        assertEquals(0, resource.getCode());
    }

    @Test
    public final void test4() {
        // ディレクトリを削除でき、終了コードが 2 であること。
        file = new File("test");
        resource = new DirectoryResource("test");
        resource.delete();

        assertFalse(file.isDirectory());
        assertEquals(2, resource.getCode());

        // 終了コードが 0 であること。
        resource.delete();
        assertEquals(0, resource.getCode());
    }

}
