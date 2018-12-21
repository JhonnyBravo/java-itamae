package mode_resource;

/**
 * ファイルまたはディレクトリのパーミッション設定を変更する。
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

            Mode resource = new Mode();
            resource.setMode(path, mode);
            System.exit(resource.getCode());
        }
    }
}
