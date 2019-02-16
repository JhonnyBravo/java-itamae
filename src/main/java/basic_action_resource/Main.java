package basic_action_resource;

import attribute_resource.AttributeResource;
import attribute_resource.GroupResource;
import attribute_resource.ModeResource;
import attribute_resource.OwnerResource;
import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

/**
 * ファイルまたはディレクトリの作成・削除と、所有者・グループ所有者・パーミッション設定を変更する。
 */
public class Main {

    /**
     * @param args
     *             <ul>
     *             <li>-F &lt;path&gt; path に指定したファイルを操作対象とする。</li>
     *             <li>-D &lt;path&gt; path に指定したディレクトリを操作対象とする。</li>
     *             <li>-c ファイルまたはディレクトリを新規作成する。</li>
     *             <li>-d ファイルまたはディレクトリを削除する。</li>
     *             <li>-o &lt;owner&gt; ファイルまたはディレクトリの所有者を owner に指定したユーザへ変更する。</li>
     *             <li>-g &lt;group&gt; ファイルまたはディレクトリのグループ所有者を group
     *             に指定したユーザへ変更する。</li>
     *             <li>-m &lt;mode&gt; ファイルまたはディレクトリのパーミッションを mode に指定した値へ変更する。</li>
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

        String filePath = null;
        String dirPath = null;
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
                filePath = options.getOptarg();
                fileFlag = 1;
                break;
            case 'D':
                dirPath = options.getOptarg();
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

        ActionResource actResource = null;
        AttributeResource attrResource = null;
        String path = null;
        int status = 0;

        if (fileFlag == 0 && dirFlag == 0) {
            System.err.println("file オプションまたは directory オプションを指定してください。");
            System.exit(1);
        }

        if (createFlag == 1) {
            if (fileFlag == 1) {
                actResource = new FileResource(filePath);
                path = filePath;
            } else if (dirFlag == 1) {
                actResource = new DirectoryResource(dirPath);
                path = dirPath;
            }

            actResource.create();
            status = actResource.getCode();

            if (status == 1) {
                System.exit(status);
            }

            if (ownerFlag == 1) {
                attrResource = new OwnerResource(path, owner);
                attrResource.setAttribute();
                status = attrResource.getCode();

                if (status == 1) {
                    System.exit(status);
                }
            }

            if (groupFlag == 1) {
                attrResource = new GroupResource(path, group);
                attrResource.setAttribute();
                status = attrResource.getCode();

                if (status == 1) {
                    System.exit(status);
                }
            }

            if (modeFlag == 1) {
                attrResource = new ModeResource(path, mode);
                attrResource.setAttribute();
                status = attrResource.getCode();

                if (status == 1) {
                    System.exit(status);
                }
            }
        } else if (deleteFlag == 1) {
            if (fileFlag == 1) {
                actResource = new FileResource(filePath);
            } else if (dirFlag == 1) {
                actResource = new DirectoryResource(dirPath);
            }

            actResource.delete();
            status = actResource.getCode();

            if (status == 1) {
                System.exit(status);
            }
        }

        System.exit(status);
    }

}
