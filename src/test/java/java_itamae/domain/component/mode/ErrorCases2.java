package java_itamae.domain.component.mode;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
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

  private ModeComponent component;
  private Path path;

  @Before
  public void setUp() throws Exception {
    component = new ModeComponentImpl();
    path = component.convertToPath("test.txt");
    Files.createFile(path);
  }

  @After
  public void tearDown() throws Exception {
    Files.delete(path);
  }

  /**
   * 不正なパーミッション設定値を指定して {@link ModeComponent#updateMode(String, String)} 実行した場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>パーミッション設定値が変更されないこと。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Theory
  public void mre001(String mode) throws Exception {
    final Set<PosixFilePermission> curPermission = component.getMode(path.toFile().getPath());
    final int status = component.updateMode(path.toFile().getPath(), mode);
    final Set<PosixFilePermission> newPermission = component.getMode(path.toFile().getPath());

    assertThat(status, is(1));
    assertThat(curPermission.equals(newPermission), is(true));
  }
}
