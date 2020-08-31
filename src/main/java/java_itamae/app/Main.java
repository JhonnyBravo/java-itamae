package java_itamae.app;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import java_itamae.app.directory.CreateDirectory;
import java_itamae.app.directory.DeleteDirectory;
import java_itamae.app.file.CreateFile;
import java_itamae.app.file.DeleteFile;
import java_itamae.app.validator.IsValidAttribute;
import java_itamae.domain.model.Attribute;

/**
 * ファイルまたはディレクトリに対して以下の操作を実行する。
 * <ul>
 * <li>新規作成</li>
 * <li>削除</li>
 * <li>所有者の変更</li>
 * <li>グループ所有者の変更</li>
 * <li>パーミッション設定の変更</li>
 * </ul>
 */
public class Main {
    /**
     * @param args
     *            <ul>
     *            <li>--file, -F &lt;path&gt;<br>
     *            path に指定したファイルを操作対象とする。</li>
     *            <li>--directory, -D &lt;path&gt;<br>
     *            path に指定したディレクトリを操作対象とする。</li>
     *            <li>--create, -c<br>
     *            ファイルまたはディレクトリを新規作成する。</li>
     *            <li>--delete, -d<br>
     *            ファイルまたはディレクトリを削除する。</li>
     *            <li>--recursive, -r<br>
     *            ディレクトリを再帰的に操作する。</li>
     *            <li>--owner, -o &lt;user_name&gt;<br>
     *            ファイルまたはディレクトリの所有者を変更する。</li>
     *            <li>--group, -g &lt;group_name&gt;<br>
     *            ファイルまたはディレクトリのグループ所有者を変更する。</li>
     *            <li>--mode, -m &lt;permission&gt;<br>
     *            ファイルまたはディレクトリのパーミッション設定を変更する。<br>
     *            permission は UNIX 形式の数列 3 桁で指定する。</li>
     *            </ul>
     */
    public static void main(String[] args) {
        final LongOpt[] longopts = new LongOpt[8];
        longopts[0] = new LongOpt("file", LongOpt.REQUIRED_ARGUMENT, null, 'F');
        longopts[1] = new LongOpt("directory", LongOpt.REQUIRED_ARGUMENT, null,
                'D');
        longopts[2] = new LongOpt("create", LongOpt.NO_ARGUMENT, null, 'c');
        longopts[3] = new LongOpt("delete", LongOpt.NO_ARGUMENT, null, 'd');
        longopts[4] = new LongOpt("recursive", LongOpt.NO_ARGUMENT, null, 'r');
        longopts[5] = new LongOpt("owner", LongOpt.REQUIRED_ARGUMENT, null,
                'o');
        longopts[6] = new LongOpt("group", LongOpt.REQUIRED_ARGUMENT, null,
                'g');
        longopts[7] = new LongOpt("mode", LongOpt.REQUIRED_ARGUMENT, null, 'm');

        final Getopt options = new Getopt("Main", args, "F:D:cdro:g:m:",
                longopts);

        int c;
        int fileFlag = 0;
        int dirFlag = 0;
        int createFlag = 0;
        int deleteFlag = 0;

        boolean recursive = false;
        int status = 0;

        final Attribute attr = new Attribute();
        final Usage usage = new Usage();

        if (args.length == 0) {
            usage.run();
        }

        while ((c = options.getopt()) != -1) {
            switch (c) {
                case 'F' :
                    attr.setPath(options.getOptarg());
                    fileFlag = 1;
                    break;
                case 'D' :
                    attr.setPath(options.getOptarg());
                    dirFlag = 1;
                    break;
                case 'c' :
                    createFlag = 1;
                    break;
                case 'd' :
                    deleteFlag = 1;
                    break;
                case 'r' :
                    recursive = true;
                    break;
                case 'o' :
                    attr.setOwner(options.getOptarg());
                    break;
                case 'g' :
                    attr.setGroup(options.getOptarg());
                    break;
                case 'm' :
                    attr.setMode(options.getOptarg());
                    break;
                default :
                    usage.run();
                    status = 1;
                    break;
            }
        }

        if (new IsValidAttribute().test(attr)) {
            if (createFlag == 1) {
                if (fileFlag == 1) {
                    status = new CreateFile().apply(attr);
                } else if (dirFlag == 1) {
                    status = new CreateDirectory().apply(attr, recursive);
                }
            } else if (deleteFlag == 1) {
                if (fileFlag == 1) {
                    status = new DeleteFile().apply(attr);
                } else if (dirFlag == 1) {
                    status = new DeleteDirectory().apply(attr, recursive);
                }
            }
        } else {
            status = 1;
        }

        System.exit(status);
    }
}
