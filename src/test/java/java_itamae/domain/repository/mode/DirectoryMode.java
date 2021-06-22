package java_itamae.domain.repository.mode;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.File;
import java.io.FileNotFoundException;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * ディレクトリに対するパーミッション変更のテスト。
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(ModeRepositoryImpl.class)
public class DirectoryMode {
  @Inject
  private ModeRepository repository;
  private File directory;

  @Before
  public void setUp() throws Exception {
    directory = new File("test_dir");
    directory.mkdir();
  }

  @After
  public void tearDown() throws Exception {
    directory.delete();
  }

  // owner パーミッション
  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test1_1() throws Exception {
    final boolean status = repository.updateMode("test_dir", "100");
    assertThat(status, is(true));
  }

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test1_2() throws Exception {
    final boolean status = repository.updateMode("test_dir", "200");
    assertThat(status, is(true));
  }

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test1_3() throws Exception {
    final boolean status = repository.updateMode("test_dir", "300");
    assertThat(status, is(true));
  }

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test1_4() throws Exception {
    final boolean status = repository.updateMode("test_dir", "400");
    assertThat(status, is(true));
  }

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test1_5() throws Exception {
    final boolean status = repository.updateMode("test_dir", "500");
    assertThat(status, is(true));
  }

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test1_6() throws Exception {
    final boolean status = repository.updateMode("test_dir", "600");
    assertThat(status, is(true));
  }

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test1_7() throws Exception {
    final boolean status = repository.updateMode("test_dir", "700");
    assertThat(status, is(true));
  }

  // group パーミッション

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test2_1() throws Exception {
    final boolean status = repository.updateMode("test_dir", "010");
    assertThat(status, is(true));
  }

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test2_2() throws Exception {
    final boolean status = repository.updateMode("test_dir", "020");
    assertThat(status, is(true));
  }

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test2_3() throws Exception {
    final boolean status = repository.updateMode("test_dir", "030");
    assertThat(status, is(true));
  }

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test2_4() throws Exception {
    final boolean status = repository.updateMode("test_dir", "040");
    assertThat(status, is(true));
  }

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test2_5() throws Exception {
    final boolean status = repository.updateMode("test_dir", "050");
    assertThat(status, is(true));
  }

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test2_6() throws Exception {
    final boolean status = repository.updateMode("test_dir", "060");
    assertThat(status, is(true));
  }

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test2_7() throws Exception {
    final boolean status = repository.updateMode("test_dir", "070");
    assertThat(status, is(true));
  }

  // others パーミッション

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test3_1() throws Exception {
    final boolean status = repository.updateMode("test_dir", "001");
    assertThat(status, is(true));
  }

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test3_2() throws Exception {
    final boolean status = repository.updateMode("test_dir", "002");
    assertThat(status, is(true));
  }

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test3_3() throws Exception {
    final boolean status = repository.updateMode("test_dir", "003");
    assertThat(status, is(true));
  }

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test3_4() throws Exception {
    final boolean status = repository.updateMode("test_dir", "004");
    assertThat(status, is(true));
  }

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test3_5() throws Exception {
    final boolean status = repository.updateMode("test_dir", "005");
    assertThat(status, is(true));
  }

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test3_6() throws Exception {
    final boolean status = repository.updateMode("test_dir", "006");
    assertThat(status, is(true));
  }

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test3_7() throws Exception {
    final boolean status = repository.updateMode("test_dir", "007");
    assertThat(status, is(true));
  }

  // owner, group, others パーミッション同時変更

  /**
   * パーミッション設定を変更できて終了ステータスが true であること。
   *
   * @throws Exception {@link Exception}
   */
  @Test
  public void test4_1() throws Exception {
    final boolean status = repository.updateMode("test_dir", "740");
    assertThat(status, is(true));
  }

  /**
   * 新しく設定するパーミッション設定値が現在設定されているパーミッション設定値と同一である場合に終了ステータスが false であること。
   *
   * @throws Exception
   */
  @Test
  public void test5_1() throws Exception {
    repository.updateMode("test_dir", "777");
    final boolean status = repository.updateMode("test_dir", "777");
    assertThat(status, is(false));
  }

  /**
   * 存在しないディレクトリに対して {@link ModeRepository#updateMode(String, String)} を実行した場合に
   * {@link FileNotFoundException} が送出されること。
   *
   * @throws FileNotFoundException {@link FileNotFoundException}
   */
  @Test(expected = FileNotFoundException.class)
  public void test6_1() throws Exception {
    try {
      repository.updateMode("NotExist", "640");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  // パーミッション値が 3 桁ではない場合

  /**
   * 不正なパーミッション設定値が指定された場合に {@link Exception} が送出されること。
   *
   * @throws Exception {@link Exception}
   */
  @Test(expected = IllegalArgumentException.class)
  public void test7_1() throws Exception {
    try {
      repository.updateMode("test_dir", "6410");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   * 不正なパーミッション設定値が指定された場合に {@link Exception} が送出されること。
   *
   * @throws Exception {@link Exception}
   */
  @Test(expected = IllegalArgumentException.class)
  public void test7_2() throws Exception {
    try {
      repository.updateMode("test_dir", "64");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  // パーミッション値に数字以外の文字列が含まれている場合

  /**
   * 不正なパーミッション設定値が指定された場合に {@link Exception} が送出されること。
   *
   * @throws Exception {@link Exception}
   */
  @Test(expected = IllegalArgumentException.class)
  public void test7_3() throws Exception {
    try {
      repository.updateMode("test_dir", "a42");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   * 不正なパーミッション設定値が指定された場合に {@link Exception} が送出されること。
   *
   * @throws Exception {@link Exception}
   */
  @Test(expected = IllegalArgumentException.class)
  public void test7_4() throws Exception {
    try {
      repository.updateMode("test_dir", "-42");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   * 不正なパーミッション設定値が指定された場合に {@link Exception} が送出されること。
   *
   * @throws Exception {@link Exception}
   */
  @Test(expected = IllegalArgumentException.class)
  public void test7_5() throws Exception {
    try {
      repository.updateMode("test_dir", "+42");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  // 7 より大きい数値が含まれている場合

  /**
   * 不正なパーミッション設定値が指定された場合に {@link Exception} が送出されること。
   *
   * @throws Exception {@link Exception}
   */
  @Test(expected = IllegalArgumentException.class)
  public void test7_6() throws Exception {
    try {
      repository.updateMode("test_dir", "871");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   * 不正なパーミッション設定値が指定された場合に {@link Exception} が送出されること。
   *
   * @throws Exception {@link Exception}
   */
  @Test(expected = IllegalArgumentException.class)
  public void test7_7() throws Exception {
    try {
      repository.updateMode("test_dir", "781");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   * 不正なパーミッション設定値が指定された場合に {@link Exception} が送出されること。
   *
   * @throws Exception {@link Exception}
   */
  @Test(expected = IllegalArgumentException.class)
  public void test7_8() throws Exception {
    try {
      repository.updateMode("test_dir", "768");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }
}
