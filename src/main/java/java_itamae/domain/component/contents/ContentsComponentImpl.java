package java_itamae.domain.component.contents;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.status.Status;

public class ContentsComponentImpl implements ContentsComponent {

  @Override
  public List<String> getContents(final ContentsModel model) throws Exception {
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
  @SuppressWarnings("unused")
  public Status updateContents(final ContentsModel model, final List<String> contents)
      throws Exception {
    Status status = Status.INIT;

    try (BufferedWriter buffer = new BufferedWriter(this.getWriter(model))) {
      for (final String line : contents) {
        buffer.write(line);
        buffer.newLine();
      }

      status = Status.DONE;
    } catch (final Exception e) {
      throw e;
    }

    return status;
  }

  @Override
  public Status deleteContents(final ContentsModel model) throws Exception {
    Status status = Status.INIT;

    try {
      final List<String> curContents = this.getContents(model);

      if (!curContents.isEmpty()) {
        try (BufferedWriter buffer = new BufferedWriter(this.getWriter(model))) {
          buffer.write("");
          status = Status.DONE;
        }
      }
    } catch (final Exception e) {
      throw e;
    }

    return status;
  }
}
