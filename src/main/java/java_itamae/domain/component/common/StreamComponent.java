package java_itamae.domain.component.common;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java_itamae.domain.model.contents.ContentsModel;

public interface StreamComponent extends BaseComponent {
  public default Reader getReader(ContentsModel model) throws Exception {
    final Path path = this.convertToPath(model.getPath());

    if (!path.toFile().isFile()) {
      throw new FileNotFoundException(model.getPath() + " が見つかりません。");
    }

    return Files.newBufferedReader(path, Charset.forName(model.getEncoding()));
  }

  public default Writer getWriter(ContentsModel model) throws Exception {
    final Path path = this.convertToPath(model.getPath());

    if (!path.toFile().isFile()) {
      throw new FileNotFoundException(model.getPath() + " が見つかりません。");
    }

    return Files.newBufferedWriter(path, Charset.forName(model.getEncoding()));
  }
}
