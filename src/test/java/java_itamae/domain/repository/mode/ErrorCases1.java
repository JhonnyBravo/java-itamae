package java_itamae.domain.repository.mode;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

/** 例外発生時のテスト。 */
// @Ignore("Windows の場合は非対応である為、実行しない。")
public class ErrorCases1 {
  private ModeRepository mr;

  @Before
  public void setUp() throws Exception {
    mr = new ModeRepositoryImpl();
  }

  /** 存在しないディレクトリのパーミッション設定値を変更しようとした場合に {@link FileNotFoundException} が送出されること。 */
  @Test(expected = FileNotFoundException.class)
  public void mre001() throws Exception {
    try {
      mr.updateMode("NotExist", "640");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /** 存在しないファイルのパーミッション設定値を変更しようとした場合に {@link FileNotFoundException} が送出されること。 */
  @Test(expected = FileNotFoundException.class)
  public void mre002() throws Exception {
    try {
      mr.updateMode("NotExist.txt", "640");
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }
}
