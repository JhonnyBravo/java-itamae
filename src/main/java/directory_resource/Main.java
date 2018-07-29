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
     *             </ul>
     */
    public static void main(String[] args) {
        LongOpt[] longopts = new LongOpt[2];
        longopts[0] = new LongOpt("create", LongOpt.REQUIRED_ARGUMENT, null, 'c');
        longopts[1] = new LongOpt("delete", LongOpt.REQUIRED_ARGUMENT, null, 'd');

        Getopt options = new Getopt("Main", args, "c:d:", longopts);

        int c;
        int cFlag = 0;
        int dFlag = 0;
        String path = null;

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
            }
        }

        DirectoryController dc = new DirectoryController(path);

        if (cFlag == 1) {
            dc.create();
        } else if (dFlag == 1) {
            dc.delete();
        }

        System.exit(dc.getCode());
    }

}
