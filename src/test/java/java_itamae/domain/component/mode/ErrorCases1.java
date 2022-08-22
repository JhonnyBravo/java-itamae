package java_itamae.domain.component.mode;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.inject.Inject;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Rule;
import org.junit.Test;

/** 例外発生時のテスト。 */
// @Ignore("Windows の場合は非対応である為、実行しない。")
public class ErrorCases1 {
  @Inject private ModeComponent component;

  @Rule
  public WeldInitiator weld = WeldInitiator.from(ModeComponentImpl.class).inject(this).build();

  /**
   * 存在しないディレクトリのパーミッション設定値を変更しようとした場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void mre001() throws Exception {
    final int status = component.updateMode("NotExist", "640");
    assertThat(status, is(1));
  }

  /**
   * 存在しないファイルのパーミッション設定値を変更しようとした場合に
   *
   * <ul>
   *   <li>異常終了すること。
   *   <li>終了ステータスが 1 であること。
   * </ul>
   */
  @Test
  public void mre002() throws Exception {
    final int status = component.updateMode("NotExist.txt", "640");
    assertThat(status, is(1));
  }
}
