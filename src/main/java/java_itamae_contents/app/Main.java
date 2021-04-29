package java_itamae_contents.app;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import java_itamae_contents.app.contents.AppendContent;
import java_itamae_contents.app.contents.DeleteContents;
import java_itamae_contents.app.contents.GetContents;
import java_itamae_contents.app.contents.SetContent;
import java_itamae_contents.app.validator.IsValidContentsAttribute;
import java_itamae_contents.domain.model.ContentsAttribute;

/**
 * テキストファイルの読み書きを実行する。
 */
public class Main {
  /**
   * テキストファイルの読み書きを実行する。
   *
   * @param args
   *        <ul>
   *        <li>--path, -p &lt;path&gt;: 操作対象とするテキストファイルのパスを指定する。</li>
   *        <li>--encoding, -e &lt;encoding&gt;: 文字エンコーディングを指定する。</li>
   *        <li>--set-content, -s &lt;content&gt;: テキストファイルを上書きする。</li>
   *        <li>--append-content, -a &lt;content&gt;: テキストファイル末尾へ文字列を追記する。</li>
   *        <li>--delete-content, -d: テキストファイルの内容を削除して空にする。</li>
   *        <li>--get-content, -g: テキストファイルの内容を読込んで標準出力へ出力する。。</li>
   *        </ul>
   */
  public static void main(String[] args) {
    final LongOpt[] longopts = new LongOpt[6];
    longopts[0] = new LongOpt("path", LongOpt.REQUIRED_ARGUMENT, null, 'p');
    longopts[1] = new LongOpt("encoding", LongOpt.REQUIRED_ARGUMENT, null, 'e');
    longopts[2] = new LongOpt("set-content", LongOpt.REQUIRED_ARGUMENT, null, 's');
    longopts[3] = new LongOpt("append-content", LongOpt.REQUIRED_ARGUMENT, null, 'a');
    longopts[4] = new LongOpt("get-content", LongOpt.NO_ARGUMENT, null, 'g');
    longopts[5] = new LongOpt("delete-content", LongOpt.NO_ARGUMENT, null, 'd');

    final Getopt options = new Getopt("Main", args, "p:e:s:a:gd", longopts);

    final ContentsAttribute attr = new ContentsAttribute();

    int c;
    int setFlag = 0;
    int appendFlag = 0;
    int getFlag = 0;
    int deleteFlag = 0;

    String content = null;

    int status = 0;
    final Usage usage = new Usage();

    if (args.length == 0) {
      usage.run();
    }

    while ((c = options.getopt()) != -1) {
      switch (c) {
        case 'p':
          attr.setPath(options.getOptarg());
          break;
        case 'e':
          attr.setEncoding(options.getOptarg());
          break;
        case 's':
          content = options.getOptarg();
          setFlag = 1;
          break;
        case 'a':
          content = options.getOptarg();
          appendFlag = 1;
          break;
        case 'g':
          getFlag = 1;
          break;
        case 'd':
          deleteFlag = 1;
          break;
        default:
          usage.run();
          status = 1;
          break;
      }
    }

    if (new IsValidContentsAttribute().test(attr)) {
      if (appendFlag == 1) {
        status = new AppendContent().apply(attr, content);
      } else if (setFlag == 1) {
        status = new SetContent().apply(attr, content);
      } else if (deleteFlag == 1) {
        status = new DeleteContents().apply(attr);
      } else if (getFlag == 1) {
        status = new GetContents().apply(attr);
      }
    } else {
      status = 1;
    }

    System.exit(status);
  }
}
