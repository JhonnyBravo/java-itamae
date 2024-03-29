package java_itamae.domain.component.mode;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import java_itamae.domain.model.status.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/** ディレクトリのパーミッション設定値を変更する場合のテスト。 */
// @Ignore("Windows の場合は非対応である為、実行しない。")
@RunWith(Theories.class)
public class UpdateDirectoryMode {
  // owner パーミッション
  @DataPoint public static String OWNER1 = "100";
  @DataPoint public static String OWNER2 = "200";
  @DataPoint public static String OWNER3 = "300";
  @DataPoint public static String OWNER4 = "400";
  @DataPoint public static String OWNER5 = "500";
  @DataPoint public static String OWNER6 = "600";
  @DataPoint public static String OWNER7 = "700";

  // group パーミッション
  @DataPoint public static String GROUP1 = "010";
  @DataPoint public static String GROUP2 = "020";
  @DataPoint public static String GROUP3 = "030";
  @DataPoint public static String GROUP4 = "040";
  @DataPoint public static String GROUP5 = "050";
  @DataPoint public static String GROUP6 = "060";
  @DataPoint public static String GROUP7 = "070";

  // others パーミッション
  @DataPoint public static String OTHERS1 = "001";
  @DataPoint public static String OTHERS2 = "002";
  @DataPoint public static String OTHERS3 = "003";
  @DataPoint public static String OTHERS4 = "004";
  @DataPoint public static String OTHERS5 = "005";
  @DataPoint public static String OTHERS6 = "006";
  @DataPoint public static String OTHERS7 = "007";

  // owner, group, others パーミッション同時変更
  @DataPoint public static String ALL = "740";

  private ModeComponent component;
  private Path path;

  @Before
  public void setUp() throws Exception {
    component = new ModeComponentImpl();
    path = component.convertToPath("test_dir");
    Files.createDirectory(path);
  }

  @After
  public void tearDown() throws Exception {
    Files.delete(path);
  }

  /**
   * 以下の検証を実施する。
   *
   * <ul>
   *   <li>{@link ModeComponent#updateMode(String, String)} 実行後の返り値の確認。
   *   <li>ディレクトリのパーミッション値変更確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>ディレクトリのパーミッション設定値が変更されること。
   * </ul>
   */
  @Theory
  public void mrs001(String mode) throws Exception {
    final Set<PosixFilePermission> curPermission = component.getMode(path.toFile().getPath());
    final Status status = component.updateMode(path.toFile().getPath(), mode);
    final Set<PosixFilePermission> newPermission = component.getMode(path.toFile().getPath());

    assertThat(status, is(Status.DONE));
    assertThat(curPermission.equals(newPermission), is(false));
  }

  /**
   * 新しく設定するパーミッション設定値が、現在設定されているパーミッション設定値と同一である場合の動作検証を実施する。
   *
   * <ul>
   *   <li>{@link ModeComponent#updateMode(String, String)} 実行後の返り値の確認。
   *   <li>ディレクトリのパーミッション値変更確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#INIT} が返されること。
   *   <li>パーミッション設定値が変更されていないこと。
   * </ul>
   */
  @Theory
  public void mrs002(String mode) throws Exception {
    component.updateMode(path.toFile().getPath(), mode);
    final Set<PosixFilePermission> curPermission = component.getMode(path.toFile().getPath());
    final Status status = component.updateMode("test_dir", mode);
    final Set<PosixFilePermission> newPermission = component.getMode(path.toFile().getPath());

    assertThat(status, is(Status.INIT));
    assertThat(curPermission.equals(newPermission), is(true));
  }
}
