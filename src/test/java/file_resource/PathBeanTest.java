package file_resource;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * {@link file_resource.PathBean} の単体テスト。
 */
public class PathBeanTest {

    /**
    * <p>
    * {@link file_resource.PathBean#getBaseName()},
    * {@link file_resource.PathBean#setBaseName(java.lang.String)} のためのテスト・メソッド。
    * </p>
    * <p>
    * {@link file_resource.PathBean#setBaseName(java.lang.String)}
    * に指定されたファイル名が返されることを確認する。
    * </p>
    */
    @Test
    public void testBaseName() {
        PathBean pb = new PathBean();
        pb.setBaseName("test_base_name.txt");

        String actual = pb.getBaseName();//実際の値
        String expected = "test_base_name.txt";//期待値

        assertEquals(expected, actual);
    }

    /**
    * <p>
    * {@link file_resource.PathBean#getDirName()},
    * {@link file_resource.PathBean#setDirName(java.lang.String)} のためのテスト・メソッド。
    * </p>
    * <p>
    * {@link file_resource.PathBean#setDirName(java.lang.String)}
    * に指定されたディレクトリパスが返されることを確認する。
    * </p>
    */
    @Test
    public void testDirName() {
        PathBean pb = new PathBean();
        pb.setDirName("test_dir_name");

        String actual = pb.getDirName();
        String expected = "test_dir_name";

        assertEquals(expected, actual);
    }

    /**
    * <p>
    * {@link file_resource.PathBean#getPath()} のためのテスト・メソッド。
    * </p>
    * <p>
    * {@link file_resource.PathBean#setDirName(java.lang.String)} に指定されたディレクトリ名と
    * {@link file_resource.PathBean#setBaseName(java.lang.String)} に指定されたファイル名を連結し、
    * 正規化されたパスが返されることを確認する。
    * </p>
    */
    @Test
    public void testPath1() {
        String expected = null;
        StatusBean sb = new StatusBean();
        sb.setCode(2);

        PathBean pb = new PathBean();
        pb.setDirName("test_dir");
        pb.setBaseName("test_file.txt");
        String actual = pb.getPath();

        try {
            expected = new File("test_dir", "test_file.txt").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assertEquals(expected, actual);
            assertEquals(sb.getCode(), pb.getCode());
        }
    }

    /**
    * <p>
    * {@link file_resource.PathBean#getPath()} のためのテスト・メソッド。
    * </p>
    * <p>
    * {@link file_resource.PathBean#setDirName(java.lang.String)} に指定されたディレクトリ名の
    * 正規化されたパスが返されることを確認する。
    * </p>
    */
    @Test
    public void testPath2() {
        String expected = null;
        StatusBean sb = new StatusBean();
        sb.setCode(2);

        PathBean pb = new PathBean();
        pb.setDirName("test_dir");
        String actual = pb.getPath();

        try {
            expected = new File("test_dir").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assertEquals(expected, actual);
            assertEquals(sb.getCode(), pb.getCode());
        }
    }

    /**
    * <p>
    * {@link file_resource.PathBean#getPath()} のためのテスト・メソッド。
    * </p>
    * <p>
    * {@link file_resource.PathBean#setBaseName(java.lang.String)} に指定されたファイル名の
    * 正規化されたパスが返されることを確認する。
    * </p>
    */
    @Test
    public void testPath3() {
        String expected = null;
        StatusBean sb = new StatusBean();
        sb.setCode(2);

        PathBean pb = new PathBean();
        pb.setBaseName("test_file.txt");
        String actual = pb.getPath();

        try {
            expected = new File("test_file.txt").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assertEquals(expected, actual);
            assertEquals(sb.getCode(), pb.getCode());
        }
    }

}
