package basic_action_resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     *             に指定したグループへ変更する。</li>
     *             <li>-m, --mode &lt;mode&gt; ファイルまたはディレクトリのパーミッションを mode
     *             に指定した値へ変更する。</li>
     *             </ul>
     */
    public static void main(String[] args) {
        final LongOpt[] longopts = new LongOpt[7];
        longopts[0] = new LongOpt("file", LongOpt.REQUIRED_ARGUMENT, null, 'F');
        longopts[1] = new LongOpt("directory", LongOpt.REQUIRED_ARGUMENT, null, 'D');
        longopts[2] = new LongOpt("create", LongOpt.NO_ARGUMENT, null, 'c');
        longopts[3] = new LongOpt("delete", LongOpt.NO_ARGUMENT, null, 'd');
        longopts[4] = new LongOpt("owner", LongOpt.REQUIRED_ARGUMENT, null, 'o');
        longopts[5] = new LongOpt("group", LongOpt.REQUIRED_ARGUMENT, null, 'g');
        longopts[6] = new LongOpt("mode", LongOpt.REQUIRED_ARGUMENT, null, 'm');

        final Getopt options = new Getopt("Main", args, "F:D:cdo:g:m:", longopts);
        final Logger logger = LoggerFactory.getLogger(Main.class);

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
            logger.warn("file オプションまたは directory オプションを指定してください。");
            System.exit(1);
        }

        if (createFlag == 0 && (ownerFlag == 1 || groupFlag == 1 || modeFlag == 1)) {
            logger.warn("create オプションを指定してください。");
            System.exit(1);
        }

        if (fileFlag == 1) {
            resource = file;
        } else if (dirFlag == 1) {
            resource = directory;
        }

        boolean status = false;

        try {
            if (createFlag == 1) {
                // リソース作成
                status = resource.create();

                // 所有者変更
                if (ownerFlag == 1) {
                    status = resource.setOwner(owner);
                }

                // グループ所有者変更
                if (groupFlag == 1) {
                    status = resource.setGroup(group);
                }

                // パーミッション変更
                if (modeFlag == 1) {
                    status = resource.setMode(mode);
                }
            } else if (deleteFlag == 1) {
                status = resource.delete();
            }
        } catch (final Exception e) {
            logger.warn("エラーが発生しました。", e);
            System.exit(1);
        }

        if (status) {
            System.exit(2);
        } else {
            System.exit(0);
        }
    }
}
