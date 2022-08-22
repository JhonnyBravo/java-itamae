package java_itamae.domain.component.mode;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import javax.inject.Inject;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/** ファイルのパーミッション設定値を変更する場合のテスト */
// @Ignore("Windows の場合は非対応である為、実行しない。")
@RunWith(Theories.class)
public class UpdateFileMode {
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
  @Inject private ModeComponent component;
  private Path path;

  @Rule
  public WeldInitiator weld = WeldInitiator.from(ModeComponentImpl.class).inject(this).build();

  @Before
  public void setUp() throws Exception {
    path = component.convertToPath("test.txt");
    Files.createFile(path);
  }

  @After
  public void tearDown() throws Exception {
    Files.delete(path);
  }

  /**
   *
   *
   * <ul>
   *   <li>ファイルのパーミッション設定値が変更されること。
   *   <li>終了ステータスが 2 であること。
   * </ul>
   */
  @Theory
  public void mrs001(String mode) throws Exception {
    final Set<PosixFilePermission> curPermission = component.getMode(path.toFile().getPath());
    final int status = component.updateMode(path.toFile().getPath(), mode);
    final Set<PosixFilePermission> newPermission = component.getMode(path.toFile().getPath());

    assertThat(status, is(2));
    assertThat(curPermission.equals(newPermission), is(false));
  }

  /**
   * 新しく設定するパーミッション設定値が、現在設定されているパーミッション設定値と同一である場合に
   *
   * <ul>
   *   <li>パーミッション設定値が変更されないこと。
   *   <li>終了ステータスが 0 であること。
   * </ul>
   */
  @Theory
  public void mrs002(String mode) throws Exception {
    component.updateMode(path.toFile().getPath(), mode);
    final Set<PosixFilePermission> curPermission = component.getMode(path.toFile().getPath());
    final int status = component.updateMode(path.toFile().getPath(), mode);
    final Set<PosixFilePermission> newPermission = component.getMode(path.toFile().getPath());

    assertThat(status, is(0));
    assertThat(curPermission.equals(newPermission), is(true));
  }
}
