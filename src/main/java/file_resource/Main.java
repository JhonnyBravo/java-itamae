package file_resource;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

/**
 * ファイルを作成または削除する。
 */
public class Main {
    /**
    * @param args
    * <ul>
    * <li>-c, --create &lt;path&gt; ファイルを新規作成する。</li>
    * <li>-d, --delete &lt;path&gt; ファイルを削除する。</li>
    * </ul>
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

        FileController fc = new FileController(path);

        if (cFlag == 1) {
            fc.create();
        } else if (dFlag == 1) {
            fc.delete();
        }

        System.exit(fc.getCode());
    }

}
