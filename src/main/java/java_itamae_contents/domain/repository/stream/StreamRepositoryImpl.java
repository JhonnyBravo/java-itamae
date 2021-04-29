package java_itamae_contents.domain.repository.stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
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

    final Reader reader =
        new InputStreamReader(new FileInputStream(attr.getPath()), attr.getEncoding());
    return reader;
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

    final Writer writer =
        new OutputStreamWriter(new FileOutputStream(attr.getPath()), attr.getEncoding());
    return writer;
  }
}
