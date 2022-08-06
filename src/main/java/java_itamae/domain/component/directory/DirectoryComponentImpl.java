package java_itamae.domain.component.directory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

@Service
public class DirectoryComponentImpl implements DirectoryComponent {
  private final BiFunction<String, Boolean, Integer> createDirectory =
      (path, recursive) -> {
        int status = 0;
        final Path p = this.convertToPath(path);

        if (p.toFile().isDirectory()) {
          return status;
        }

        this.getLogger().info("{} を作成しています......", path);

        try {
          if (recursive) {
            Files.createDirectories(p);
            status = 2;
          } else {
            Files.createDirectory(p);
            status = 2;
          }
        } catch (final IOException e) {
          this.getLogger().warn(e.toString());
          status = 1;
        }

        return status;
      };
  private final BiFunction<String, Boolean, Integer> deleteDirectory =
      (path, recursive) -> {
        int status = 0;
        final Path p = this.convertToPath(path);

        if (!p.toFile().isDirectory()) {
          return status;
        }

        this.getLogger().info("{} を削除しています......", path);

        try {
          if (recursive) {
            status = this.deleteRecursive(path);
          } else {
            Files.delete(p);
            status = 2;
          }
        } catch (final IOException e) {
          this.getLogger().warn(e.toString());
          status = 1;
        }

        return status;
      };

  @Override
  public int create(String path, boolean recursive) {
    return this.createDirectory.apply(path, recursive);
  }

  @Override
  public int delete(String path, boolean recursive) {
    return this.deleteDirectory.apply(path, recursive);
  }

  private int deleteRecursive(String path) {
    int status = 0;
    final Path p = this.convertToPath(path);

    if (!p.toFile().isDirectory()) {
      return status;
    }

    try {
      for (final File file : p.toFile().listFiles()) {
        if (file.isDirectory()) {
          deleteRecursive(file.getPath());
        } else {
          // ディレクトリ配下のファイル群を削除する。
          Files.delete(file.toPath());
        }
      }

      // 空になったディレクトリを削除する。
      Files.delete(p);
      status = 2;
    } catch (final IOException e) {
      this.getLogger().warn(e.toString());
      status = 1;
    }

    return status;
  }
}
