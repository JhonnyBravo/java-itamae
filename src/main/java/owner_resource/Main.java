package owner_resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * ファイルまたはディレクトリの所有者を変更する。
 */
@SpringBootApplication
public class Main {
    /**
     * @param args
     *             <ol>
     *             <li>path 操作対象とするファイルまたはディレクトリのパスを指定する。</li>
     *             <li>owner ファイルまたはディレクトリに新しい所有者として設定するユーザ名を指定する。</li>
     *             </ol>
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        String path = null;
        String owner = null;

        if (args.length > 0) {
            if (args.length < 2) {
                System.err.println("owner を指定してください。");
                System.exit(1);
            }

            path = args[0];
            owner = args[1];

            SetUserPrincipal sup = context.getBean(SetUserPrincipal.class);
            sup.init(path, owner);
            sup.runCommand();

            System.exit(sup.getCode());
        }
    }
}
