package file_resource;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

/**
 * {@link file_resource.FileController} の単体テスト。
 */
public class FileControllerTest {

    /**
    * {@link file_resource.FileController#create()},
    * {@link file_resource.FileController#getCode()} の単体テスト。
    */
    @Test
    public void test1() {
        FileController fc = new FileController("test.txt");
        fc.create();

        File f = new File("test.txt");
        //ファイルが作成されていること。
        assertTrue(f.isFile());
        //終了コードが 2 であること。
        assertEquals(2, fc.getCode());

        fc.create();
        //終了コードが 0 であること。
        assertEquals(0, fc.getCode());
    }

    @Test
    public void test2() {
        FileController fc = new FileController("notExist/test.txt");
        fc.create();

        File f = new File("notExist/test.txt");
        //ファイル作成されていないこと。
        assertFalse(f.isFile());
        //終了コードが 1 であること。
        assertEquals(1, fc.getCode());
    }

    /**
    * {@link file_resource.FileController#delete()},
    * {@link file_resource.FileController#getCode()} の単体テスト。
    */
    @Test
    public void test3() {
        FileController fc = new FileController("test.txt");
        fc.delete();

        File f = new File("test.txt");
        //ファイルが削除されていること。
        assertFalse(f.isFile());
        //終了コードが 2 であること。
        assertEquals(2, fc.getCode());

        fc.delete();
        //終了コードが 0 であること。
        assertEquals(0, fc.getCode());
    }

}
