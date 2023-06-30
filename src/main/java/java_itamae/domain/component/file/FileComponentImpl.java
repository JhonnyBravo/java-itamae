package java_itamae.domain.component.file;

import java.nio.file.Files;
import java.nio.file.Path;
import java_itamae.domain.model.status.Status;

public class FileComponentImpl implements FileComponent {
  @Override
  public Status create(final String path) throws Exception {
    Status status = Status.INIT;
    final Path convertedPath = this.convertToPath(path);

    if (!convertedPath.toFile().isFile()) {
      Files.createFile(convertedPath);
      status = Status.DONE;
      this.getLogger().info("created file: {}", path);
    }

    return status;
  }

  @Override
  public Status delete(final String path) throws Exception {
    Status status = Status.INIT;
    final Path convertedPath = this.convertToPath(path);

    if (convertedPath.toFile().isFile()) {
      Files.delete(convertedPath);
      status = Status.DONE;
      this.getLogger().info("deleted file: {}", path);
    }

    return status;
  }
}
