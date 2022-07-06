package java_itamae.app;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import java_itamae.app.directory.CreateDirectory;
import java_itamae.app.directory.DeleteDirectory;
import java_itamae.app.file.CreateFile;
import java_itamae.app.file.DeleteFile;
import java_itamae.app.template.DeleteContents;
import java_itamae.app.template.GetContents;
import java_itamae.app.template.GetProperties;
import java_itamae.app.template.UpdateContents;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.contents.IsValidContentsModel;
import java_itamae.domain.model.directory.DirectoryResourceModel;
import java_itamae.domain.model.directory.DirectoryResourceModelValidator;
import java_itamae.domain.model.file.FileResourceModel;
import java_itamae.domain.model.file.FileResourceModelValidator;
import java_itamae.domain.model.template.TemplateResourceModel;
import java_itamae.domain.model.template.TemplateResourceModelValidator;

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
  public static void main(String[] args) {
    final LongOpt[] longopts = new LongOpt[2];
    longopts[0] = new LongOpt("path", LongOpt.REQUIRED_ARGUMENT, null, 'p');
    longopts[1] = new LongOpt("encoding", LongOpt.REQUIRED_ARGUMENT, null, 'e');

    final Getopt options = new Getopt("Main", args, "p:e:", longopts);

    final ContentsModel cliArgs = new ContentsModel();

    int c;
    int status = 0;
    final Usage usage = new Usage();

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

    if (!new IsValidContentsModel().test(cliArgs)) {
      status = 1;
      System.exit(status);
    }

    // プロパティファイルからキーと値を読み込む。
    final GetProperties getProperties = new GetProperties();
    final Map<String, String> properties = getProperties.apply(cliArgs);

    // resource_name を取得し、実行するリソースを判定する。
    final String resourceName = properties.get("resource_name");
    final String action = properties.get("action");

    if (resourceName != null && resourceName.equals("file")) {
      final FileResourceModel resourceModel = new FileResourceModel();
      resourceModel.setAction(properties.get("action"));
      resourceModel.setPath(properties.get("path"));
      resourceModel.setGroup(properties.get("group"));
      resourceModel.setMode(properties.get("mode"));
      resourceModel.setOwner(properties.get("owner"));

      final FileResourceModelValidator validator = new FileResourceModelValidator();

      if (!validator.test(resourceModel)) {
        status = 1;
        System.exit(status);
      }

      if (action.equals("create")) {
        final CreateFile createFile = new CreateFile();
        status = createFile.apply(resourceModel);
      } else if (action.equals("delete")) {
        final DeleteFile deleteFile = new DeleteFile();
        status = deleteFile.apply(resourceModel);
      }
    } else if (resourceName != null && resourceName.equals("directory")) {
      final DirectoryResourceModel resourceModel = new DirectoryResourceModel();
      resourceModel.setAction(properties.get("action"));
      resourceModel.setGroup(properties.get("group"));
      resourceModel.setMode(properties.get("mode"));
      resourceModel.setOwner(properties.get("owner"));
      resourceModel.setPath(properties.get("path"));
      resourceModel.setRecursive(properties.get("recursive"));

      final DirectoryResourceModelValidator validator = new DirectoryResourceModelValidator();

      if (!validator.test(resourceModel)) {
        status = 1;
        System.exit(status);
      }

      if (action.equals("create")) {
        final CreateDirectory createDirectory = new CreateDirectory();
        status = createDirectory.apply(resourceModel);
      } else if (action.equals("delete")) {
        final DeleteDirectory deleteDirectory = new DeleteDirectory();
        status = deleteDirectory.apply(resourceModel);
      }
    } else if (resourceName != null && resourceName.equals("template")) {
      // 各プロパティの値をモデルへ設定する。
      final TemplateResourceModel resourceModel = new TemplateResourceModel();
      resourceModel.setAction(properties.get("action"));
      resourceModel.setPath(properties.get("path"));
      resourceModel.setSource(properties.get("source"));
      resourceModel.setEncoding(properties.get("encoding"));

      // バリデーションチェックを実行する。
      final TemplateResourceModelValidator validator = new TemplateResourceModelValidator();

      if (!validator.test(resourceModel)) {
        status = 1;
        System.exit(status);
      }

      // 動作を実行する。
      if (action.equals("create")) {
        // 読込み対象とするテキストファイル
        final ContentsModel sourceAttr = new ContentsModel();
        sourceAttr.setPath(resourceModel.getSource());
        sourceAttr.setEncoding(resourceModel.getEncoding());

        final GetContents getContents = new GetContents();
        final List<String> contents = getContents.apply(sourceAttr);

        // 書込み対象とするテキストファイル
        final ContentsModel targetAttr = new ContentsModel();
        targetAttr.setPath(resourceModel.getPath());
        targetAttr.setEncoding(resourceModel.getEncoding());

        final UpdateContents updateContents = new UpdateContents();
        status = updateContents.apply(targetAttr, contents);
      } else if (action.equals("delete")) {
        final ContentsModel targetAttr = new ContentsModel();
        targetAttr.setPath(resourceModel.getPath());
        targetAttr.setEncoding(resourceModel.getEncoding());

        final DeleteContents deleteContents = new DeleteContents();
        status = deleteContents.apply(targetAttr);
      }
    } else {
      final Logger logger = LoggerFactory.getLogger(Main.class);
      logger.warn("resource_name が指定されていない、または不正な値です。");
      status = 1;
    }

    System.exit(status);
  }
}
