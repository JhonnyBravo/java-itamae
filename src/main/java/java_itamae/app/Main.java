package java_itamae.app;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import java_itamae.domain.model.Attribute;
import java_itamae.domain.service.directory.DirectoryService;
import java_itamae.domain.service.directory.DirectoryServiceImpl;
import java_itamae.domain.service.file.FileService;
import java_itamae.domain.service.file.FileServiceImpl;

/**
 * ファイルまたはディレクトリに対して以下の操作を実行する。
 * <ul>
 * <li>新規作成</li>
 * <li>削除</li>
 * <li>所有者の変更</li>
 * <li>グループ所有者の変更</li>
 * <li>パーミッション設定の変更</li>
 * </ul>
 */
public class Main {
    /**
     * @param args
     *            <ul>
     *            <li>--file, -F &lt;path&gt;<br>
     *            path に指定したファイルを操作対象とする。</li>
     *            <li>--directory, -D &lt;path&gt;<br>
     *            path に指定したディレクトリを操作対象とする。</li>
     *            <li>--create, -c<br>
     *            ファイルまたはディレクトリを新規作成する。</li>
     *            <li>--delete, -d<br>
     *            ファイルまたはディレクトリを削除する。</li>
     *            <li>--recursive, -r<br>
     *            ディレクトリを再帰的に操作する。</li>
     *            <li>--owner, -o &lt;user_name&gt;<br>
     *            ファイルまたはディレクトリの所有者を変更する。</li>
     *            <li>--group, -g &lt;group_name&gt;<br>
     *            ファイルまたはディレクトリのグループ所有者を変更する。</li>
     *            <li>--mode, -m &lt;permission&gt;<br>
     *            ファイルまたはディレクトリのパーミッション設定を変更する。<br>
     *            permission は UNIX 形式の数列 3 桁で指定する。</li>
     *            </ul>
     */
    public static void main(String[] args) {
        final LongOpt[] longopts = new LongOpt[8];
        longopts[0] = new LongOpt("file", LongOpt.REQUIRED_ARGUMENT, null, 'F');
        longopts[1] = new LongOpt("directory", LongOpt.REQUIRED_ARGUMENT, null,
                'D');
        longopts[2] = new LongOpt("create", LongOpt.NO_ARGUMENT, null, 'c');
        longopts[3] = new LongOpt("delete", LongOpt.NO_ARGUMENT, null, 'd');
        longopts[4] = new LongOpt("recursive", LongOpt.NO_ARGUMENT, null, 'r');
        longopts[5] = new LongOpt("owner", LongOpt.REQUIRED_ARGUMENT, null,
                'o');
        longopts[6] = new LongOpt("group", LongOpt.REQUIRED_ARGUMENT, null,
                'g');
        longopts[7] = new LongOpt("mode", LongOpt.REQUIRED_ARGUMENT, null, 'm');

        final Getopt options = new Getopt("Main", args, "F:D:cdro:g:m:",
                longopts);

        int c;
        int fileFlag = 0;
        int dirFlag = 0;
        int createFlag = 0;
        int deleteFlag = 0;
        int recursiveFlag = 0;

        final Attribute attr = new Attribute();

        while ((c = options.getopt()) != -1) {
            switch (c) {
                case 'F' :
                    attr.setPath(options.getOptarg());
                    fileFlag = 1;
                    break;
                case 'D' :
                    attr.setPath(options.getOptarg());
                    dirFlag = 1;
                    break;
                case 'c' :
                    createFlag = 1;
                    break;
                case 'd' :
                    deleteFlag = 1;
                    break;
                case 'r' :
                    recursiveFlag = 1;
                    break;
                case 'o' :
                    attr.setOwner(options.getOptarg());
                    break;
                case 'g' :
                    attr.setGroup(options.getOptarg());
                    break;
                case 'm' :
                    attr.setMode(options.getOptarg());
                    break;
            }
        }

        final Logger logger = LoggerFactory.getLogger(Main.class);

        Predicate<Attribute> isValid = attribute -> {
            final Validator validator = Validation
                    .buildDefaultValidatorFactory().getValidator();
            final Set<ConstraintViolation<Attribute>> validResult = validator
                    .validate(attr);

            if (validResult.size() > 0) {
                validResult.stream().forEach(e -> {
                    final String path = e.getPropertyPath().toString();
                    final String message = e.getMessage();
                    logger.warn(path + ": " + message);
                });

                return false;
            } else {
                return true;
            }
        };

        final FileService fs = new FileServiceImpl();

        final Function<Attribute, Integer> createFile = attribute -> {
            boolean result = false;

            try {
                result = fs.create(attribute);

                if (result) {
                    return 2;
                } else {
                    return 0;
                }
            } catch (Exception e) {
                logger.warn(e.toString());
                return 1;
            }
        };

        final Function<Attribute, Integer> deleteFile = attribute -> {
            boolean result = false;

            try {
                result = fs.delete(attribute);

                if (result) {
                    return 2;
                } else {
                    return 0;
                }
            } catch (Exception e) {
                logger.warn(e.toString());
                return 1;
            }
        };

        final DirectoryService ds = new DirectoryServiceImpl();

        if (recursiveFlag == 1) {
            ds.setRecursive(true);
        }

        final Function<Attribute, Integer> createDirectory = attribute -> {
            try {
                boolean result = ds.create(attribute);

                if (result) {
                    return 2;
                } else {
                    return 0;
                }
            } catch (Exception e) {
                logger.warn(e.toString());
                return 1;
            }
        };

        final Function<Attribute, Integer> deleteDirectory = attribute -> {
            try {
                boolean result = ds.delete(attribute);

                if (result) {
                    return 2;
                } else {
                    return 0;
                }
            } catch (Exception e) {
                logger.warn(e.toString());
                return 1;
            }
        };

        int status = 0;

        if (isValid.test(attr)) {
            if (createFlag == 1) {
                if (fileFlag == 1) {
                    status = createFile.apply(attr);
                } else if (dirFlag == 1) {
                    status = createDirectory.apply(attr);
                }
            } else if (deleteFlag == 1) {
                if (fileFlag == 1) {
                    status = deleteFile.apply(attr);
                } else if (dirFlag == 1) {
                    status = deleteDirectory.apply(attr);
                }
            }
        } else {
            status = 1;
        }

        System.exit(status);
    }
}
