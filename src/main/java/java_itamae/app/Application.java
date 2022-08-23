package java_itamae.app;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import java.util.Map;
import java_itamae.app.directory.DirectoryResourceImpl;
import java_itamae.app.file.FileResourceImpl;
import java_itamae.app.properties.ContentsModelValidator;
import java_itamae.app.properties.GetProperties;
import java_itamae.app.template.TemplateResourceImpl;
import java_itamae.domain.model.contents.ContentsModel;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** プロパティファイルを読込み、ファイルの設定値に従って動作を実行する。 */
public class Application {
  @Inject private Usage usage;
  @Inject private ContentsModelValidator validator;
  @Inject private GetProperties getProperties;
  @Inject private FileResourceImpl fileResource;
  @Inject private DirectoryResourceImpl directoryResource;
  @Inject private TemplateResourceImpl templateResource;
  /**
   * プロパティファイルを読込み、動作を実行する。
   *
   * @param args
   *     <ul>
   *       <li>--path, -p &lt;path&gt;: 読込み対象とするプロパティファイルのパスを指定する。
   *       <li>--encoding, -e &lt;encoding&gt;: 文字エンコーディングを指定する。
   *     </ul>
   */
  public int main(String[] args) {
    final LongOpt[] longopts = new LongOpt[2];
    longopts[0] = new LongOpt("path", LongOpt.REQUIRED_ARGUMENT, null, 'p');
    longopts[1] = new LongOpt("encoding", LongOpt.REQUIRED_ARGUMENT, null, 'e');

    final Getopt options = new Getopt("Main", args, "p:e:", longopts);

    final ContentsModel cliArgs = new ContentsModel();

    int c;
    int status = 0;

    if (args.length == 0) {
      usage.run();
      status = 1;
    }

    while ((c = options.getopt()) != -1) {
      switch (c) {
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

    if (!validator.test(cliArgs)) {
      status = 1;
      return status;
    }

    // プロパティファイルからキーと値を読み込む。
    final Map<String, String> properties = getProperties.apply(cliArgs);

    // resource_name を取得し、実行するリソースを判定する。
    final String resourceName = properties.get("resource_name");

    if (resourceName != null && resourceName.equals("file")) {
      status = fileResource.apply(properties);
    } else if (resourceName != null && resourceName.equals("directory")) {
      status = directoryResource.apply(properties);
    } else if (resourceName != null && resourceName.equals("template")) {
      status = templateResource.apply(properties);
    } else {
      final Logger logger = LoggerFactory.getLogger(Application.class);
      logger.warn("resource_name が指定されていない、または不正な値です。");
      status = 1;
    }

    return status;
  }
}
