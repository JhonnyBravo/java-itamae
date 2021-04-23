package java_itamae.app;

import java.util.ArrayList;
import java.util.List;

/**
 * CLI コマンドの使用法を表示する。
 */
public class Usage implements Runnable {
  /**
   * CLI コマンドの使用法を表示する。
   */
  @Override
  public void run() {
    final List<String> optionList = new ArrayList<>();

    optionList.add("--file, -F <path>: path に指定したファイルに対して操作を実行します。");
    optionList.add("--directory, -D <path>: path に指定したディレクトリに対して操作を実行します。");
    optionList.add("--create, -c: ファイルまたはディレクトリを新規作成します。");
    optionList.add("--delete, -d: ファイルまたはディレクトリを削除します。");
    optionList.add("--recursive, -r: ディレクトリを再帰的に操作します。");
    optionList.add("--owner, -o <user_name>: ファイルまたはディレクトリの所有者を変更します。");
    optionList.add("--group, -g <group_name>: ファイルまたはディレクトリのグループ所有者を変更します。");
    optionList.add(
        "--mode, -m <permission>: ファイルまたはディレクトリのパーミッションを変更します。パーミッション値は UNIX 形式の数列 3 桁で指定してください。");

    optionList.forEach(option -> {
      System.out.println(option);
    });
  }

}
