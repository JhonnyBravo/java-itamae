package mode_resource;

/**
 * ファイルまたはディレクトリのパーミッション変更を実行する。
 */
public class Main {
    /**
     * ファイルまたはディレクトリのパーミッションを変更する。
     * 
     * @param args
     *             <ol>
     *             <li>path 操作対象とするファイルまたはディレクトリのパスを指定する。</li>
     *             <li>mode ファイルまたはディレクトリに設定するパーミッションを指定する。</li>
     *             </ol>
     */
    public static void main(String[] args) {
        String path = null;
        String mode = null;

        if (args.length > 0) {
            if (args.length < 2) {
                System.err.println("mode を指定してください。");
                System.exit(1);
            }

            path = args[0];
            mode = args[1];

            SetPermission sp = new SetPermission();
            sp.init(path, mode);
            sp.runCommand();

            System.exit(sp.getCode());
        }
    }
}
