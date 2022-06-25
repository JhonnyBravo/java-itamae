package java_itamae_contents.app;

import java.util.ArrayList;
import java.util.List;

/** CLI コマンドの使用法を表示する。 */
public class Usage implements Runnable {
  /** CLI コマンドの使用法を表示する。 */
  @Override
  public void run() {
    final List<String> optionList = new ArrayList<>();

    optionList.add("プロパティファイルを読込み、ファイルの設定値に従って動作を実行します。");
    optionList.add("--path, -p <path>: 読込み対象とするプロパティのパスを指定します。");
    optionList.add("--encoding, -e <encoding>: 読込み時に使用する文字エンコーディングを指定します。");

    optionList.forEach(
        option -> {
          System.out.println(option);
        });
  }
}
