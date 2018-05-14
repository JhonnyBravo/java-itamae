package file_resource;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import common_resource.PathBean;
import common_resource.StatusBean;

/**
 * {@link file_resource.FileAction} の単体テスト。
 */
public class FileActionTest {

    /**
    * <p>
    * {@link file_resource.FileAction#create()},
    * {@link file_resource.FileAction#delete()} のためのテスト・メソッド。
    * </p>
    *
    * <p>create 確認事項</p>
    *
    * <ol>
    * <li>ファイルが存在しない場合に新規作成されること。</li>
    * <li>ファイルを新規作成した場合に終了コード 2 が返されること。</li>
    * <li>ファイルを新規作成した場合にステータス文字列が出力されること。</li>
    * <li>ファイルが既に存在する場合に終了コード 0 が返されること。</li>
    * <li>ファイルが既に存在する場合にステータス文字列が出力されないこと。</li>
    * </ol>
    *
    * <p>delete 確認事項</p>
    *
    * <ol>
    * <li>ファイルが存在する場合に削除されること。</li>
    * <li>ファイルを削除した場合に終了コード 2 が返されること。</li>
    * <li>ファイルを削除した場合にステータス文字列が出力されること。</li>
    * <li>ファイルが存在しない場合に終了コード 0 が返されること。</li>
    * <li>ファイルが存在しない場合にステータス文字列が出力されないこと。</li>
    * </ol>
    */
    @Test
    public void test1() {
        String expectPath = null;

        try {
            expectPath = new File("test.txt").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //新規作成テスト
        StatusBean expectStatus = new StatusBean();
        expectStatus.setResourceName("file_resource");
        expectStatus.setCode(2);
        expectStatus.setMessage(expectPath + " を作成しました。");

        FileAction fa = new FileAction("test.txt");
        fa.create();

        assertTrue(new File(expectPath).isFile());
        assertEquals(expectStatus.getCode(), fa.getCode());
        assertEquals(expectStatus.getStatus(), fa.getStatus());

        //ファイルが既に存在する場合のテスト
        expectStatus.setResourceName(null);
        expectStatus.setCode(0);
        expectStatus.setMessage(null);

        fa.create();

        assertEquals(expectStatus.getCode(), fa.getCode());
        assertEquals(expectStatus.getStatus(), fa.getStatus());

        //削除テスト
        expectStatus.setResourceName("file_resource");
        expectStatus.setCode(2);
        expectStatus.setMessage(expectPath + " を削除しました。");

        fa.delete();

        assertFalse(new File(expectPath).isFile());
        assertEquals(expectStatus.getCode(), fa.getCode());
        assertEquals(expectStatus.getStatus(), fa.getStatus());

        //ファイルが存在しない場合のテスト
        expectStatus.setResourceName(null);
        expectStatus.setCode(0);
        expectStatus.setMessage(null);

        fa.delete();

        assertEquals(expectStatus.getCode(), fa.getCode());
        assertEquals(expectStatus.getStatus(), fa.getStatus());
    }

    /**
    * <p>
    * {@link file_resource.FileAction#setCwd()},
    * {@link file_resource.FileAction#create()},
    * {@link file_resource.FileAction#delete()} のためのテスト・メソッド。
    * </p>
    *
    * <p>create 確認事項</p>
    *
    * <ol>
    * <li>ファイルが存在しない場合に新規作成されること。</li>
    * <li>ファイルを新規作成した場合に終了コード 2 が返されること。</li>
    * <li>ファイルを新規作成した場合にステータス文字列が出力されること。</li>
    * <li>ファイルが既に存在する場合に終了コード 0 が返されること。</li>
    * <li>ファイルが既に存在する場合にステータス文字列が出力されないこと。</li>
    * </ol>
    *
    * <p>delete 確認事項</p>
    *
    * <ol>
    * <li>ファイルが存在する場合に削除されること。</li>
    * <li>ファイルを削除した場合に終了コード 2 が返されること。</li>
    * <li>ファイルを削除した場合にステータス文字列が出力されること。</li>
    * <li>ファイルが存在しない場合に終了コード 0 が返されること。</li>
    * <li>ファイルが存在しない場合にステータス文字列が出力されないこと。</li>
    * </ol>
    */
    @Test
    public void test2() {
        String dirName = new File(System.getProperty("user.dir")).getParent();
        String baseName = "test.txt";

        PathBean pb = new PathBean();
        pb.setDirName(dirName);
        pb.setBaseName(baseName);

        String expectPath = pb.getPath();

        //新規作成テスト
        StatusBean expectStatus = new StatusBean();
        expectStatus.setResourceName("file_resource");
        expectStatus.setCode(2);
        expectStatus.setMessage(expectPath + " を作成しました。");

        FileAction fa = new FileAction(baseName);
        fa.setCwd(dirName);
        fa.create();

        assertTrue(new File(expectPath).isFile());
        assertEquals(expectStatus.getCode(), fa.getCode());
        assertEquals(expectStatus.getStatus(), fa.getStatus());

        //ファイルが既に存在する場合のテスト
        expectStatus.setResourceName(null);
        expectStatus.setCode(0);
        expectStatus.setMessage(null);

        fa.create();

        assertEquals(expectStatus.getCode(), fa.getCode());
        assertEquals(expectStatus.getStatus(), fa.getStatus());

        //削除テスト
        expectStatus.setResourceName("file_resource");
        expectStatus.setCode(2);
        expectStatus.setMessage(expectPath + " を削除しました。");

        fa.delete();

        assertFalse(new File(expectPath).isFile());
        assertEquals(expectStatus.getCode(), fa.getCode());
        assertEquals(expectStatus.getStatus(), fa.getStatus());

        //ファイルが存在しない場合のテスト
        expectStatus.setResourceName(null);
        expectStatus.setCode(0);
        expectStatus.setMessage(null);

        fa.delete();

        assertEquals(expectStatus.getCode(), fa.getCode());
        assertEquals(expectStatus.getStatus(), fa.getStatus());
    }

}
