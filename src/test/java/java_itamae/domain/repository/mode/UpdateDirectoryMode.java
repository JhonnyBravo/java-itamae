package java_itamae.domain.repository.mode;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;

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

  private ModeRepository mr;
  private File directory;

  @Before
  public void setUp() throws Exception {
    mr = new ModeRepositoryImpl();
    directory = new File("test_dir");
    directory.mkdir();
  }

  @After
  public void tearDown() throws Exception {
    directory.delete();
  }

  /**
   *
   *
   * <ul>
   *   <li>ディレクトリのパーミッション設定値が変更されること。
   *   <li>終了ステータスが true であること。
   * </ul>
   */
  @Theory
  public void mrs001(String mode) throws Exception {
    final boolean status = mr.updateMode("test_dir", mode);
    assertThat(status, is(true));
  }

  /** 新しく設定するパーミッション設定値が、現在設定されているパーミッション設定値と同一である場合に終了ステータスが false であること。 */
  @Theory
  public void mrs002(String mode) throws Exception {
    mr.updateMode("test_dir", mode);
    final boolean status = mr.updateMode("test_dir", mode);
    assertThat(status, is(false));
  }
}
