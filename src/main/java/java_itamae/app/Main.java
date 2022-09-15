package java_itamae.app;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import java.util.Map;
import java_itamae.app.common.BaseResource;
import java_itamae.app.directory.DirectoryResourceImpl;
import java_itamae.app.file.FileResourceImpl;
import java_itamae.app.properties.ContentsModelValidator;
import java_itamae.app.properties.GetProperties;
import java_itamae.app.template.TemplateResourceImpl;
import java_itamae.domain.model.contents.ContentsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** プロパティファイルを読込み、ファイルの設定値に従って動作を実行する。 */
public class Main {
  /**
   * プロパティファイルを読込み、動作を実行する。
   *
   * @param args
   *     <ul>
   *       <li>--path, -p &lt;path&gt;: 読込み対象とするプロパティファイルのパスを指定する。
   *       <li>--encoding, -e &lt;encoding&gt;: 文字エンコーディングを指定する。
   *     </ul>
   */
  @SuppressWarnings("unused")
  public static void main(final String[] args) {
    final LongOpt[] longopts = new LongOpt[2];
    longopts[0] = new LongOpt("path", LongOpt.REQUIRED_ARGUMENT, null, 'p');
    longopts[1] = new LongOpt("encoding", LongOpt.REQUIRED_ARGUMENT, null, 'e');

    final Getopt options = new Getopt("Main", args, "p:e:", longopts);

    final ContentsModel cliArgs = new ContentsModel();

    int option;
    int status = 0;
    final Usage usage = new Usage();

    if (args.length == 0) {
      usage.run();
      status = 1;
    }

    while ((option = options.getopt()) != -1) {
      switch (option) {
        case 'p':
          cliArgs.setPath(options.getOptarg());
          break;
        case 'e':
          cliArgs.setEncoding(options.getOptarg());
          break;
        default:
          usage.run();
          status = 1;
          break;
      }
    }

    if (!new ContentsModelValidator().test(cliArgs)) {
      status = 1;
      System.exit(status);
    }

    // プロパティファイルからキーと値を読み込む。
    final GetProperties getProperties = new GetProperties();
    final Map<String, String> properties = getProperties.apply(cliArgs);

    // resource_name を取得し、実行するリソースを判定する。
    final String resourceName = properties.get("resource_name");

    if ("file".equals(resourceName)) {
      final BaseResource<?> resource = new FileResourceImpl();
      status = resource.apply(properties);
    } else if ("directory".equals(resourceName)) {
      final BaseResource<?> resource = new DirectoryResourceImpl();
      status = resource.apply(properties);
    } else if ("template".equals(resourceName)) {
      final BaseResource<?> resource = new TemplateResourceImpl();
      status = resource.apply(properties);
    } else {
      final Logger logger = LoggerFactory.getLogger(Main.class);
      logger.warn("resource_name が指定されていない、または不正な値です。");
      status = 1;
    }

    System.exit(status);
  }
}
