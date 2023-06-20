package java_itamae.domain.component.mode;

import jakarta.inject.Inject;
import java.io.FileNotFoundException;
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
   * 存在しないディレクトリのパーミッション設定値を変更しようとした場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link FileNotFoundException} が発生すること。
   * </ul>
   */
  @Test(expected = FileNotFoundException.class)
  public void mre001() throws Exception {
    component.updateMode("NotExist", "640");
  }

  /**
   * 存在しないファイルのパーミッション設定値を変更しようとした場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   * </ul>
   *
   * <ul>
   *   <li>{@link FileNotFoundException} が発生すること。
   * </ul>
   */
  @Test(expected = FileNotFoundException.class)
  public void mre002() throws Exception {
    component.updateMode("NotExist.txt", "640");
  }
}
