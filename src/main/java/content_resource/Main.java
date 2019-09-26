package content_resource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

/**
 * テキストファイルへの読み書きを実行する。
 */
public class Main {

    /**
     * @param args
     *             <ul>
     *             <li>-p, --path &lt;path&gt; 操作対象とするファイルのパスを指定する。</li>
     *             <li>-c, --create &lt;content&gt; ファイルへ追記する文字列を指定する。</li>
     *             <li>-u, --update &lt;content&gt; ファイルへ上書きする文字列を指定する。</li>
     *             <li>-d, --delete ファイルを空にする。</li>
     *             <li>-r, --read ファイルを読込み、標準出力へ出力する。</li>
     *             </ul>
     */
    public static void main(String[] args) {
        final LongOpt[] longopts = new LongOpt[5];
        longopts[0] = new LongOpt("path", LongOpt.REQUIRED_ARGUMENT, null, 'p');
        longopts[1] = new LongOpt("create", LongOpt.REQUIRED_ARGUMENT, null, 'c');
        longopts[2] = new LongOpt("update", LongOpt.REQUIRED_ARGUMENT, null, 'u');
        longopts[3] = new LongOpt("delete", LongOpt.NO_ARGUMENT, null, 'd');
        longopts[4] = new LongOpt("read", LongOpt.NO_ARGUMENT, null, 'r');

        final Getopt options = new Getopt("Main", args, "p:c:u:dr", longopts);
        final Logger logger = LoggerFactory.getLogger(Main.class);

        String path = null;
        String content = null;

        int c;
        int createFlag = 0;
        int updateFlag = 0;
        int deleteFlag = 0;
        int readFlag = 0;

        while ((c = options.getopt()) != -1) {
            switch (c) {
            case 'p':
                path = options.getOptarg();
                break;
            case 'c':
                content = options.getOptarg();
                createFlag = 1;
                break;
            case 'u':
                content = options.getOptarg();
                updateFlag = 1;
                break;
            case 'd':
                deleteFlag = 1;
                break;
            case 'r':
                readFlag = 1;
                break;
            }
        }

        if (path == null) {
            logger.warn("path オプションを指定してください。");
            System.exit(1);
        }

        final ContentResource<List<String>> resource = new TxtResource(path);
        final File file = new File(path);
        boolean status = false;

        try {
            if (createFlag == 1 || updateFlag == 1) {
                if (content == null) {
                    logger.warn("content を指定してください。");
                    System.exit(1);
                }

                final List<String> newContents = new ArrayList<String>();
                newContents.add(content);

                if (createFlag == 1) {
                    if (!file.isFile()) {
                        file.createNewFile();
                    }

                    final List<String> curContents = resource.getContent();
                    curContents.addAll(newContents);
                    status = resource.setContent(curContents);
                } else if (updateFlag == 1) {
                    status = resource.setContent(newContents);
                }
            } else if (readFlag == 1) {
                final List<String> curContents = resource.getContent();

                if (curContents.size() > 0) {
                    for (final String line : curContents) {
                        System.out.println(line);
                    }

                    status = true;
                }
            } else if (deleteFlag == 1) {
                status = resource.deleteContent();
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
