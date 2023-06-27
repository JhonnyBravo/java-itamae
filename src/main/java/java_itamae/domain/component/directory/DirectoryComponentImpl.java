package java_itamae.domain.component.directory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java_itamae.domain.model.status.Status;
import org.springframework.stereotype.Service;

@Service
public class DirectoryComponentImpl implements DirectoryComponent {
  @Override
  public Status create(final String path, final boolean recursive) throws Exception {
    Status status = Status.INIT;
    final Path convertedPath = this.convertToPath(path);

    if (!convertedPath.toFile().isDirectory()) {
      if (recursive) {
        Files.createDirectories(convertedPath);
      } else {
        Files.createDirectory(convertedPath);
      }

      status = Status.DONE;
      this.getLogger().info("created directory: {}", path);
    }

    return status;
  }

  @Override
  public Status delete(final String path, final boolean recursive) throws Exception {
    Status status = Status.INIT;
    final Path convertedPath = this.convertToPath(path);

    if (convertedPath.toFile().isDirectory()) {
      if (recursive) {
        status = this.deleteRecursive(path);
      } else {
        Files.delete(convertedPath);
        status = Status.DONE;
        this.getLogger().info("deleted directory: {}", path);
      }
    }

    return status;
  }

  /**
   * ディレクトリを一括削除する。
   *
   * <ol>
   *   <li>変数 status へ {@link Status#INIT} を設定する。
   *   <li>引数 path に指定されたディレクトリのパスが存在するか確認する。
   *       <ul>
   *         <li>存在する場合、ディレクトリ配下に存在するファイル及びディレクトリを一括削除する。
   *         <li>存在しない場合、後続処理へ遷移する。
   *       </ul>
   *   <li>空になったディレクトリを削除し、変数 status へ {@link Status#DONE} を設定する。ディレクトリの削除中に例外が発生した場合、例外を投げて異常終了する。
   *   <li>変数 status を返り値として返す。
   * </ol>
   *
   * @param path 操作対象とするディレクトリのパスを指定する。
   * @return status {@link Status} を返す。
   * @throws Exception ディレクトリの削除中に発生した例外を投げる。
   */
  private Status deleteRecursive(final String path) throws Exception {
    Status status = Status.INIT;
    final Path convertedPath = this.convertToPath(path);

    if (convertedPath.toFile().isDirectory()) {
      for (final File file : convertedPath.toFile().listFiles()) {
        if (file.isDirectory()) {
          deleteRecursive(file.getPath());
        } else {
          // ディレクトリ配下のファイル群を削除する。
          Files.delete(file.toPath());
        }
      }

      // 空になったディレクトリを削除する。
      Files.delete(convertedPath);
      status = Status.DONE;
      this.getLogger().info("deleted directory: {}", path);
    }

    return status;
  }
}
