package directory_resource;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

/**
 * ディレクトリを作成または削除する。
 */
public class Main {
    /**
     * @param args
     *             <ul>
     *             <li>-c, --create &lt;path&gt; ディレクトリを新規作成する。</li>
     *             <li>-d, --delete &lt;path&gt; ディレクトリを削除する。</li>
     *             <li>-o, --owner &lt;owner&gt; ディレクトリの所有者を変更する。</li>
     *             <li>-g, --group &lt;group&gt; グループ所有者を変更する。</li>
     *             <li>-m, --mode &lt;mode&gt; ディレクトリのパーミッションを変更する。</li>
     *             </ul>
     */
    public static void main(String[] args) {
        LongOpt[] longopts = new LongOpt[5];
        longopts[0] = new LongOpt("create", LongOpt.REQUIRED_ARGUMENT, null, 'c');
        longopts[1] = new LongOpt("delete", LongOpt.REQUIRED_ARGUMENT, null, 'd');
        longopts[2] = new LongOpt("owner", LongOpt.REQUIRED_ARGUMENT, null, 'o');
        longopts[3] = new LongOpt("group", LongOpt.REQUIRED_ARGUMENT, null, 'g');
        longopts[4] = new LongOpt("mode", LongOpt.REQUIRED_ARGUMENT, null, 'm');

        Getopt options = new Getopt("Main", args, "c:d:o:g:m:", longopts);

        int c;
        int cFlag = 0;
        int dFlag = 0;
        int oFlag = 0;
        int gFlag = 0;
        int mFlag = 0;

        String path = null;
        String owner = null;
        String group = null;
        String mode = null;

        while ((c = options.getopt()) != -1) {
            switch (c) {
            case 'c':
                path = options.getOptarg();
                cFlag = 1;
                break;
            case 'd':
                path = options.getOptarg();
                dFlag = 1;
                break;
            case 'o':
                owner = options.getOptarg();
                oFlag = 1;
                break;
            case 'g':
                group = options.getOptarg();
                gFlag = 1;
                break;
            case 'm':
                mode = options.getOptarg();
                mFlag = 1;
                break;
            }
        }

        DirectoryController dc = new DirectoryController(path);

        if (cFlag == 1) {
            dc.create();

            if (dc.getCode() == 1) {
                System.exit(dc.getCode());
            }

            if (oFlag == 1) {
                dc.setOwner(owner);

                if (dc.getCode() == 1) {
                    System.exit(dc.getCode());
                }
            }

            if (gFlag == 1) {
                dc.setGroup(group);

                if (dc.getCode() == 1) {
                    System.exit(dc.getCode());
                }
            }

            if (mFlag == 1) {
                dc.setMode(mode);

                if (dc.getCode() == 1) {
                    System.exit(dc.getCode());
                }
            }
        } else if (dFlag == 1) {
            dc.delete();
        }

        System.exit(dc.getCode());
    }

}
