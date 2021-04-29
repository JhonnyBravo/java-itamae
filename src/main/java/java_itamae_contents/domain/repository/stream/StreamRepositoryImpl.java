package java_itamae_contents.domain.repository.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java_itamae_contents.domain.model.ContentsAttribute;

public class StreamRepositoryImpl implements StreamRepository {

  @Override
  public Reader getReader(ContentsAttribute attr) throws Exception {
    final File file = new File(attr.getPath());

    if (!file.isFile()) {
      throw new FileNotFoundException(attr.getPath() + " が見つかりません。");
    }

    if (attr.getEncoding() == null) {
      attr.setEncoding(System.getProperty("file.encoding"));
    }

    return Files.newBufferedReader(Paths.get(attr.getPath()), Charset.forName(attr.getEncoding()));
  }

  @Override
  public Writer getWriter(ContentsAttribute attr) throws Exception {
    final File file = new File(attr.getPath());

    if (!file.isFile()) {
      throw new FileNotFoundException(attr.getPath() + " が見つかりません。");
    }

    if (attr.getEncoding() == null) {
      attr.setEncoding(System.getProperty("file.encoding"));
    }

    return Files.newBufferedWriter(Paths.get(attr.getPath()), Charset.forName(attr.getEncoding()));
  }
}
