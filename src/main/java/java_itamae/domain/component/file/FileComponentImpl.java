package java_itamae.domain.component.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

public class FileComponentImpl implements FileComponent {
  /** {@link FileComponent#create(String)} */
  private final transient Function<String, Integer> createFile =
      path -> {
        int status = 0;
        final Path convertedPath = this.convertToPath(path);

        if (!convertedPath.toFile().isFile()) {
          this.getLogger().info("{} を作成しています......", path);

          try {
            Files.createFile(convertedPath);
            status = 2;
          } catch (final IOException e) {
            this.getLogger().warn(e.toString());
            status = 1;
          }
        }

        return status;
      };

  /** {@link FileComponent#delete(String)} */
  private final transient Function<String, Integer> deleteFile =
      path -> {
        int status = 0;
        final Path convertedPath = this.convertToPath(path);

        if (convertedPath.toFile().isFile()) {
          this.getLogger().info("{} を削除しています......", path);
          try {
            Files.delete(convertedPath);
            status = 2;
          } catch (final IOException e) {
            this.getLogger().warn(e.toString());
            status = 1;
          }
        }

        return status;
      };

  @Override
  public int create(final String path) {
    return createFile.apply(path);
  }

  @Override
  public int delete(final String path) {
    return deleteFile.apply(path);
  }
}
