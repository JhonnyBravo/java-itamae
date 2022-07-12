package java_itamae.domain.repository.mode;

import static org.junit.Assert.assertThrows;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/** 不正なパーミッション設定値が設定されている場合のテスト。 */
// @Ignore("Windows の場合は非対応である為、実行しない。")
@RunWith(Theories.class)
public class ErrorCases2 {
  // パーミッション値が 3 桁ではない場合
  @DataPoint public static String ERROR1 = "6410";
  @DataPoint public static String ERROR2 = "64";

  // パーミッション値に数字以外の文字列が含まれている場合
  @DataPoint public static String ERROR3 = "a42";
  @DataPoint public static String ERROR4 = "-42";
  @DataPoint public static String ERROR5 = "+42";

  // 7 より大きい数値が含まれている場合
  @DataPoint public static String ERROR6 = "871";
  @DataPoint public static String ERROR7 = "781";
  @DataPoint public static String ERROR8 = "768";

  private ModeRepository mr;
  private File file;

  @Before
  public void setUp() throws Exception {
    mr = new ModeRepositoryImpl();
    file = new File("test.txt");
    file.createNewFile();
  }

  @After
  public void tearDown() throws Exception {
    file.delete();
  }

  /** {@link ModeRepository#updateMode(String, String)} 実行時に {@link Exception} が送出されること。 */
  @Theory
  public void mre001(String mode) throws Exception {
    assertThrows(
        Exception.class,
        () -> {
          try {
            mr.updateMode("test.txt", mode);
          } catch (final Exception e) {
            System.err.println(e);
            throw e;
          }
        });
  }
}
