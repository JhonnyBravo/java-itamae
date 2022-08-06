package java_itamae.domain.component.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class FileComponentImpl implements FileComponent {
  private final Function<String, Integer> createFile =
      path -> {
        int status = 0;
        final Path p = this.convertToPath(path);

        if (!p.toFile().isFile()) {
          this.getLogger().info("{} を作成しています......", path);

          try {
            Files.createFile(p);
            status = 2;
          } catch (final IOException e) {
            this.getLogger().warn(e.toString());
            status = 1;
          }
        }

        return status;
      };
  private final Function<String, Integer> deleteFile =
      path -> {
        int status = 0;
        final Path p = this.convertToPath(path);

        if (p.toFile().isFile()) {
          this.getLogger().info("{} を削除しています......", path);
          try {
            Files.delete(p);
            status = 2;
          } catch (final IOException e) {
            this.getLogger().warn(e.toString());
            status = 1;
          }
        }

        return status;
      };

  @Override
  public int create(String path) {
    return createFile.apply(path);
  }

  @Override
  public int delete(String path) {
    return deleteFile.apply(path);
  }
}
