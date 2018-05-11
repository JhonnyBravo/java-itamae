package file_resource;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * {@link file_resource.StatusBean} の単体テスト。
 */
public class StatusBeanTest {

    /**
    * <p>
    * {@link file_resource.StatusBean#getResourceName()},
    * {@link file_resource.StatusBean#setResourceName(java.lang.String)} のためのテスト・メソッド。
    * </p>
    * <p>
    * {@link file_resource.StatusBean#setResourceName(java.lang.String)} に指定したリソース名が返されることを確認する。
    * </p>
    */
    @Test
    public void testResourceName() {
        StatusBean sb = new StatusBean();
        sb.setResourceName("testResource");

        String actual = sb.getResourceName();
        String expected = "testResource";

        assertEquals(expected, actual);
    }

    /**
    * <p>
    * {@link file_resource.StatusBean#getCode()},
    * {@link file_resource.StatusBean#setCode(int)} のためのテスト・メソッド。
    * </p>
    * <p>
    * {@link file_resource.StatusBean#setCode(int)} に指定したコード値が返されることを確認する。
    * </p>
    */
    @Test
    public void testCode() {
        StatusBean sb = new StatusBean();
        sb.setCode(2);

        int actual = sb.getCode();
        int expected = 2;

        assertEquals(expected, actual);
    }

    /**
    * <p>
    * {@link file_resource.StatusBean#getMessage()},
    * {@link file_resource.StatusBean#setMessage(java.lang.String)} のためのテスト・メソッド。
    * </p>
    * <p>
    * {@link file_resource.StatusBean#setMessage(java.lang.String)} に指定したメッセージが返されることを確認する。
    * </p>
    */
    @Test
    public void testMessage() {
        StatusBean sb = new StatusBean();
        sb.setMessage("error message");

        String actual = sb.getMessage();
        String expected = "error message";

        assertEquals(expected, actual);
    }

    /**
    * <p>
    * {@link file_resource.StatusBean#getStatus()} のためのテスト・メソッド。
    * </p>
    * <p>
    * {@link file_resource.StatusBean#setResourceName(java.lang.String)} と
    * {@link file_resource.StatusBean#setMessage(java.lang.String)} に指定した文字列が
    * 連結されて返されることを確認する。
    * </p>
    */
    @Test
    public void testStatus1() {
        StatusBean sb = new StatusBean();
        sb.setResourceName("testResource");
        sb.setMessage("error message");

        String actual = sb.getStatus();
        String expected = "testResource error message";

        assertEquals(expected, actual);
    }

    /**
    * <p>
    * {@link file_resource.StatusBean#getStatus()} のためのテスト・メソッド。
    * </p>
    * <p>
    * {@link file_resource.StatusBean#setMessage(java.lang.String)} に指定した文字列が
    * 返されることを確認する。
    * </p>
    */
    @Test
    public void testStatus2() {
        StatusBean sb = new StatusBean();
        sb.setMessage("error message");

        String actual = sb.getStatus();
        String expected = "error message";

        assertEquals(expected, actual);
    }

}
