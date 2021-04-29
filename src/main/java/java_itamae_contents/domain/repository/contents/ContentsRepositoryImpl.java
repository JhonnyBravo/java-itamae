package java_itamae_contents.domain.repository.contents;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContentsRepositoryImpl implements ContentsRepository {

  @Override
  public List<String> getContents(Reader reader) throws Exception {
    final List<String> contents = new ArrayList<>();

    try (BufferedReader buffer = new BufferedReader(reader)) {
      String line;

      while ((line = buffer.readLine()) != null) {
        contents.add(line);
      }
    }

    return contents;
  }

  @Override
  public boolean updateContents(Writer writer, List<String> contents) throws Exception {
    boolean status = false;

    try (BufferedWriter buffer = new BufferedWriter(writer)) {
      for (final String line : contents) {
        buffer.write(line);
        buffer.newLine();
      }

      status = true;
    }

    return status;
  }

  @Override
  public boolean deleteContents(Writer writer) throws Exception {
    boolean status = false;

    try (BufferedWriter buffer = new BufferedWriter(writer)) {
      buffer.write("");
      status = true;
    }

    return status;
  }
}
