package java_itamae;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import java.util.Map;

import java_itamae.app.Usage;
import java_itamae.app.common.BaseResource;
import java_itamae.app.directory.DirectoryResourceImpl;
import java_itamae.app.file.FileResourceImpl;
import java_itamae.app.properties.ContentsModelValidator;
import java_itamae.app.properties.GetProperties;
import java_itamae.app.template.TemplateResourceImpl;
import java_itamae.domain.model.contents.ContentsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/** プロパティファイルを読込み、ファイルの設定値に従って動作を実行する。 */
@SpringBootApplication(scanBasePackages = "java_itamae")
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
  public static void main(String[] args) {
    final ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
    final Usage usage = context.getBean(Usage.class);
    final ContentsModelValidator validator = context.getBean(ContentsModelValidator.class);
    final GetProperties getProperties = context.getBean(GetProperties.class);
    final FileResourceImpl fileResource = context.getBean(FileResourceImpl.class);
    final DirectoryResourceImpl directoryResource = context.getBean(DirectoryResourceImpl.class);
    final TemplateResourceImpl templateResource = context.getBean(TemplateResourceImpl.class);

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
      System.exit(status);
    }

    // プロパティファイルからキーと値を読み込む。
    final Map<String, String> properties = getProperties.apply(cliArgs);

    // resource_name を取得し、実行するリソースを判定する。
    final String resourceName = properties.get("resource_name");

    if (resourceName != null && resourceName.equals("file")) {
      final BaseResource<?> resource = fileResource;
      status = resource.apply(properties);
    } else if (resourceName != null && resourceName.equals("directory")) {
      final BaseResource<?> resource = directoryResource;
      status = resource.apply(properties);
    } else if (resourceName != null && resourceName.equals("template")) {
      final BaseResource<?> resource = templateResource;
      status = resource.apply(properties);
    } else {
      final Logger logger = LoggerFactory.getLogger(Main.class);
      logger.warn("resource_name が指定されていない、または不正な値です。");
      status = 1;
    }

    System.exit(status);
  }
}
