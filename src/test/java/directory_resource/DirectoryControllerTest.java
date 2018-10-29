package directory_resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * {@link directory_resource.DirectoryController} の単体テスト。
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DirectoryControllerTest {
    @Autowired
    DirectoryController dc;

    /**
     * {@link directory_resource.DirectoryController#create()},
     * {@link directory_resource.DirectoryController#delete()},
     * {@link directory_resource.DirectoryController#getCode()} のためのテスト・メソッド。
     */
    @Test
    public void test1() {
        // 単一のディレクトリを新規作成できること。
        // 終了ステータスが 2 であること。
        dc.init("test");
        dc.create();

        File f = new File("test");

        assertTrue(f.isDirectory());
        assertEquals(2, dc.getCode());

        // 終了ステータスが 0 であること。
        dc.create();
        assertEquals(0, dc.getCode());
    }

    @Test
    public void test2() {
        // 単一のディレクトリを削除できること。
        // 終了ステータスが 2 であること。
        dc.init("test");
        dc.delete();

        File f = new File("test");

        assertFalse(f.isDirectory());
        assertEquals(2, dc.getCode());

        // 終了ステータスが 0 であること。
        dc.delete();
        assertEquals(0, dc.getCode());
    }

    @Test
    public void test3() {
        // 複数階層のディレクトリを新規作成できること。
        // 終了ステータスが 2 であること。
        dc.init("test/sub1/sub2");
        dc.create();

        File f = new File("test");

        assertTrue(f.isDirectory());
        assertEquals(2, dc.getCode());

        // 終了ステータスが 0 であること。
        dc.create();
        assertEquals(0, dc.getCode());
    }

    @Test
    public void test4() {
        // 複数階層のディレクトリを削除できること。
        // 終了ステータスが 2 であること。
        dc.init("test");
        dc.delete();

        File f = new File("test");

        assertFalse(f.isDirectory());
        assertEquals(2, dc.getCode());

        // 終了ステータスが 0 であること。
        dc.delete();
        assertEquals(0, dc.getCode());
    }
}
