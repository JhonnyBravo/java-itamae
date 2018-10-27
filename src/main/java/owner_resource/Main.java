package owner_resource;

/**
 * ファイルまたはディレクトリの所有者を変更する。
 */
public class Main {
    /**
     * @param args
     *             <ol>
     *             <li>path 操作対象とするファイルまたはディレクトリのパスを指定する。</li>
     *             <li>owner ファイルまたはディレクトリに新しい所有者として設定するユーザ名を指定する。</li>
     *             </ol>
     */
    public static void main(String[] args) {
        String path = null;
        String owner = null;

        if (args.length > 0) {
            if (args.length < 2) {
                System.err.println("owner を指定してください。");
                System.exit(1);
            }

            path = args[0];
            owner = args[1];

            SetUserPrincipal sup = new SetUserPrincipal();
            sup.init(path, owner);
            sup.runCommand();

            System.exit(sup.getCode());
        }
    }
}
