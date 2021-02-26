package java_itamae_contents.app;

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

        optionList.add("--path, -p <path>: 指定したテキストファイルに対して操作を実行します。");
        optionList.add("--encoding, -e <encoding>: 文字エンコーディングを指定します。");
        optionList.add("--set-content, -s <content>: テキストファイルを上書きします。");
        optionList.add("--append-content, -a <content>: テキストファイルの末尾へ文字列を追記します。");
        optionList.add("--get-content, -g: テキストファイルの内容を読込んで標準出力へ出力します。");
        optionList.add("--delete-content, -d: テキストファイルの内容を削除して空にします。");

        optionList.forEach(option -> {
            System.out.println(option);
        });
    }
}
