package java_itamae.domain.component.directory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiFunction;

public class DirectoryComponentImpl implements DirectoryComponent {
  /** {@link DirectoryComponent#create(String, boolean)} */
  private final transient BiFunction<String, Boolean, Integer> createDirectory =
      (path, recursive) -> {
        int status = 0;
        final Path convertedPath = this.convertToPath(path);

        if (!convertedPath.toFile().isDirectory()) {
          this.getLogger().info("{} を作成しています......", path);

          try {
            if (recursive) {
              Files.createDirectories(convertedPath);
            } else {
              Files.createDirectory(convertedPath);
            }
            status = 2;
          } catch (final IOException e) {
            this.getLogger().warn(e.toString());
            status = 1;
          }
        }

        return status;
      };

  /** {@link DirectoryComponent#delete(String, boolean)} */
  private final transient BiFunction<String, Boolean, Integer> deleteDirectory =
      (path, recursive) -> {
        int status = 0;
        final Path convertedPath = this.convertToPath(path);

        if (convertedPath.toFile().isDirectory()) {
          this.getLogger().info("{} を削除しています......", path);

          try {
            if (recursive) {
              status = this.deleteRecursive(path);
            } else {
              Files.delete(convertedPath);
              status = 2;
            }
          } catch (final IOException e) {
            this.getLogger().warn(e.toString());
            status = 1;
          }
        }

        return status;
      };

  @Override
  public int create(final String path, final boolean recursive) {
    return this.createDirectory.apply(path, recursive);
  }

  @Override
  public int delete(final String path, final boolean recursive) {
    return this.deleteDirectory.apply(path, recursive);
  }

  private int deleteRecursive(final String path) {
    int status = 0;
    final Path convertedPath = this.convertToPath(path);

    if (convertedPath.toFile().isDirectory()) {
      try {
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
        status = 2;
      } catch (final IOException e) {
        this.getLogger().warn(e.toString());
        status = 1;
      }
    }

    return status;
  }
}
