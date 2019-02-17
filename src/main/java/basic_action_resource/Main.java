package basic_action_resource;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

/**
 * ファイルまたはディレクトリの作成・削除と、所有者・グループ所有者・パーミッション設定を変更する。
 */
public class Main {

    /**
     * @param args
     *             <ul>
     *             <li>-F, --file &lt;path&gt; path に指定したファイルを操作対象とする。</li>
     *             <li>-D, --directory &lt;path&gt; path に指定したディレクトリを操作対象とする。</li>
     *             <li>-c, --create ファイルまたはディレクトリを新規作成する。</li>
     *             <li>-d, --delete ファイルまたはディレクトリを削除する。</li>
     *             <li>-o, --owner &lt;owner&gt; ファイルまたはディレクトリの所有者を owner
     *             に指定したユーザへ変更する。</li>
     *             <li>-g, --group &lt;group&gt; ファイルまたはディレクトリのグループ所有者を group
     *             に指定したユーザへ変更する。</li>
     *             <li>-m, --mode &lt;mode&gt; ファイルまたはディレクトリのパーミッションを mode
     *             に指定した値へ変更する。</li>
     *             </ul>
     */
    public static void main(String[] args) {
        LongOpt[] longopts = new LongOpt[7];
        longopts[0] = new LongOpt("file", LongOpt.REQUIRED_ARGUMENT, null, 'F');
        longopts[1] = new LongOpt("directory", LongOpt.REQUIRED_ARGUMENT, null, 'D');
        longopts[2] = new LongOpt("create", LongOpt.NO_ARGUMENT, null, 'c');
        longopts[3] = new LongOpt("delete", LongOpt.NO_ARGUMENT, null, 'd');
        longopts[4] = new LongOpt("owner", LongOpt.REQUIRED_ARGUMENT, null, 'o');
        longopts[5] = new LongOpt("group", LongOpt.REQUIRED_ARGUMENT, null, 'g');
        longopts[6] = new LongOpt("mode", LongOpt.REQUIRED_ARGUMENT, null, 'm');

        Getopt options = new Getopt("Main", args, "F:D:cdo:g:m:", longopts);

        ActionResource file = null;
        ActionResource directory = null;
        ActionResource resource = null;

        String owner = null;
        String group = null;
        String mode = null;

        int c;
        int fileFlag = 0;
        int dirFlag = 0;
        int createFlag = 0;
        int deleteFlag = 0;
        int ownerFlag = 0;
        int groupFlag = 0;
        int modeFlag = 0;

        while ((c = options.getopt()) != -1) {
            switch (c) {
            case 'F':
                file = new FileResource(options.getOptarg());
                fileFlag = 1;
                break;
            case 'D':
                directory = new DirectoryResource(options.getOptarg());
                dirFlag = 1;
                break;
            case 'c':
                createFlag = 1;
                break;
            case 'd':
                deleteFlag = 1;
                break;
            case 'o':
                ownerFlag = 1;
                owner = options.getOptarg();
                break;
            case 'g':
                groupFlag = 1;
                group = options.getOptarg();
                break;
            case 'm':
                modeFlag = 1;
                mode = options.getOptarg();
                break;
            }
        }

        if (fileFlag == 0 && dirFlag == 0) {
            System.err.println("file オプションまたは directory オプションを指定してください。");
            System.exit(1);
        }

        if (createFlag == 0 && (ownerFlag == 1 || groupFlag == 1 || modeFlag == 1)) {
            System.err.println("create オプションを指定してください。");
            System.exit(1);
        }

        if (fileFlag == 1) {
            resource = file;
        } else if (dirFlag == 1) {
            resource = directory;
        }

        if (createFlag == 1) {
            // リソース作成
            resource.create();

            if (resource.getCode() == 1) {
                System.exit(resource.getCode());
            }

            // 所有者変更
            if (ownerFlag == 1) {
                resource.setOwner(owner);

                if (resource.getCode() == 1) {
                    System.exit(resource.getCode());
                }
            }

            // グループ所有者変更
            if (groupFlag == 1) {
                resource.setGroup(group);

                if (resource.getCode() == 1) {
                    System.exit(resource.getCode());
                }
            }

            // パーミッション変更
            if (modeFlag == 1) {
                resource.setMode(mode);

                if (resource.getCode() == 1) {
                    System.exit(resource.getCode());
                }
            }

            System.exit(resource.getCode());
        } else if (deleteFlag == 1) {
            // リソース削除
            resource.delete();
            System.exit(resource.getCode());
        }
    }

}
