package java_itamae.domain.component.properties;

import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.status.Status;

public class PropertiesComponentImpl implements PropertiesComponent {

  @Override
  public Map<String, String> getProperties(final ContentsModel model) throws Exception {
    final Properties properties = new Properties();

    try (Reader reader = this.getReader(model)) {
      properties.load(reader);
    }

    final Map<String, String> map = new ConcurrentHashMap<>();

    properties
        .entrySet()
        .forEach(
            entry -> {
              map.put(entry.getKey().toString(), entry.getValue().toString());
            });

    return map;
  }

  @Override
  @SuppressWarnings("unused")
  public Status updateProperties(
      final ContentsModel model, final Map<String, String> map, final String comment)
      throws Exception {
    Status status = Status.INIT;
    final Properties properties = new Properties();

    map.entrySet()
        .forEach(
            entry -> {
              properties.setProperty(entry.getKey(), entry.getValue());
            });

    try (Writer writer = this.getWriter(model)) {
      properties.store(writer, comment);
      status = Status.DONE;
    }

    return status;
  }

  @Override
  public Status deleteProperties(final ContentsModel model, final String comment) throws Exception {
    Status status = Status.INIT;

    final Map<String, String> curProperties = this.getProperties(model);

    if (!curProperties.isEmpty()) {
      final Properties properties = new Properties();

      try (Writer writer = this.getWriter(model)) {
        properties.store(writer, comment);
        status = Status.DONE;
      }
    }

    return status;
  }
}
