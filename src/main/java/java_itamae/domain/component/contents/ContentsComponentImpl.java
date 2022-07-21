package java_itamae.domain.component.contents;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

import java_itamae.domain.model.contents.ContentsModel;

public class ContentsComponentImpl implements ContentsComponent {

  @Override
  public List<String> getContents(ContentsModel model) throws Exception {
    final List<String> contents = new ArrayList<>();

    try (BufferedReader buffer = new BufferedReader(this.getReader(model))) {
      String line;

      while ((line = buffer.readLine()) != null) {
        contents.add(line);
      }
    }

    return contents;
  }

  @Override
  public int updateContents(ContentsModel model, List<String> contents) {
    int status = 0;

    try (BufferedWriter buffer = new BufferedWriter(this.getWriter(model))) {
      for (final String line : contents) {
        buffer.write(line);
        buffer.newLine();
      }

      status = 2;
    } catch (final Exception e) {
      this.getLogger().warn(e.toString());
      status = 1;
    }

    return status;
  }

  @Override
  public int deleteContents(ContentsModel model) {
    int status = 0;

    try {
      final List<String> curContents = this.getContents(model);

      if (curContents.isEmpty()) {
        return status;
      }

      try (BufferedWriter buffer = new BufferedWriter(this.getWriter(model))) {
        buffer.write("");
        status = 2;
      }
    } catch (final Exception e) {
      this.getLogger().warn(e.toString());
      status = 1;
    }

    return status;
  }
}
