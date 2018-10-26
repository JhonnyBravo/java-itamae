package group_resource;

/**
 * ファイルまたはディレクトリのグループ所有者を変更する。
 */
public class Main {
    /**
     * @param args
     *             <ol>
     *             <li>path 操作対象とするファイルまたはディレクトリのパスを指定する。</li>
     *             <li>group ファイルまたはディレクトリに新しいグループ所有者として設定するグループ名を指定する。</li>
     *             </ol>
     */
    public static void main(String[] args) {
        String path = null;
        String group = null;

        if (args.length > 0) {
            if (args.length < 2) {
                System.err.println("group を指定してください。");
                System.exit(1);
            }

            path = args[0];
            group = args[1];

            SetGroupPrincipal sgp = new SetGroupPrincipal();
            sgp.init(path, group);
            sgp.runCommand();

            System.exit(sgp.getCode());
        }
    }
}
