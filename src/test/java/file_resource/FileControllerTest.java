package file_resource;

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
 * {@link file_resource.FileController} の単体テスト。
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileControllerTest {
    @Autowired
    private FileController fc;

    /**
     * {@link file_resource.FileController#create()},
     * {@link file_resource.FileController#getCode()} の単体テスト。
     */
    @Test
    public void test1() {
        fc.init("test.txt");
        fc.create();

        File f = new File("test.txt");
        // ファイルが作成されていること。
        assertTrue(f.isFile());
        // 終了コードが 2 であること。
        assertEquals(2, fc.getCode());

        fc.create();
        // 終了コードが 0 であること。
        assertEquals(0, fc.getCode());
    }

    @Test
    public void test2() {
        fc.init("notExist/test.txt");
        fc.create();

        File f = new File("notExist/test.txt");
        // ファイル作成されていないこと。
        assertFalse(f.isFile());
        // 終了コードが 1 であること。
        assertEquals(1, fc.getCode());
    }

    /**
     * {@link file_resource.FileController#delete()},
     * {@link file_resource.FileController#getCode()} の単体テスト。
     */
    @Test
    public void test3() {
        fc.init("test.txt");
        fc.delete();

        File f = new File("test.txt");
        // ファイルが削除されていること。
        assertFalse(f.isFile());
        // 終了コードが 2 であること。
        assertEquals(2, fc.getCode());

        fc.delete();
        // 終了コードが 0 であること。
        assertEquals(0, fc.getCode());
    }

}
