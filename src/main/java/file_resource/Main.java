package file_resource;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

/**
 * コマンドライン上でファイルの作成・削除を実行する。
 */
public class Main {
    public static void main(String[] args) {
        LongOpt[] longopts = new LongOpt[3];
        longopts[0] = new LongOpt("create", LongOpt.REQUIRED_ARGUMENT, null, 'c');
        longopts[1] = new LongOpt("delete", LongOpt.REQUIRED_ARGUMENT, null, 'd');
        longopts[2] = new LongOpt("cwd", LongOpt.REQUIRED_ARGUMENT, null, 'C');

        Getopt options = new Getopt("Main", args, "c:d:C:", longopts);

        int c;
        int cFlag = 0;
        int dFlag = 0;
        int cwdFlag = 0;
        String path = null;
        String cwd = null;

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
            case 'C':
                cwd = options.getOptarg();
                cwdFlag = 1;
                break;
            }
        }

        FileAction fa = new FileAction(path);

        if (cwdFlag == 1) {
            fa.setCwd(cwd);
        }

        if (cFlag == 1) {
            fa.create();
        } else if (dFlag == 1) {
            fa.delete();
        }

        System.exit(fa.getCode());
    }

}
