package java_itamae.domain.common;

import java.util.function.Supplier;

/** テスト用の文字エンコーディングを取得する。 */
public class GetTestEncoding implements Supplier<String> {
  /**
   * テスト用の文字エンコーディングを取得する。
   *
   * @return encoding
   *     <ul>
   *       <li>OS が Windows である場合: UTF8 を返す。
   *       <li>OS が Windows ではない場合: MS932 を返す。
   *     </ul>
   */
  @Override
  public String get() {
    String encoding = null;

    if (System.getProperty("os.name").substring(0, 3).equals("Win")) {
      encoding = "UTF8";
    } else {
      encoding = "MS932";
    }

    return encoding;
  }
}
