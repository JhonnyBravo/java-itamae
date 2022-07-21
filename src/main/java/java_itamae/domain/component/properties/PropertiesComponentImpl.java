package java_itamae.domain.component.properties;

import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import java_itamae.domain.model.contents.ContentsModel;

public class PropertiesComponentImpl implements PropertiesComponent {

  @Override
  public Map<String, String> getProperties(ContentsModel model) throws Exception {
    final Properties properties = new Properties();

    try (Reader reader = this.getReader(model)) {
      properties.load(reader);
    }

    final Map<String, String> map = new HashMap<>();

    properties
        .entrySet()
        .forEach(
            entry -> {
              map.put(entry.getKey().toString(), entry.getValue().toString());
            });

    return map;
  }

  @Override
  public int updateProperties(ContentsModel model, Map<String, String> map, String comment) {
    int status = 0;
    final Properties properties = new Properties();

    map.entrySet()
        .forEach(
            entry -> {
              properties.setProperty(entry.getKey(), entry.getValue());
            });

    try (Writer writer = this.getWriter(model)) {
      properties.store(writer, comment);
      status = 2;
    } catch (final Exception e) {
      this.getLogger().warn(e.toString());
      status = 1;
    }

    return status;
  }

  @Override
  public int deleteProperties(ContentsModel model, String comment) {
    int status = 0;

    try {
      final Map<String, String> curProperties = this.getProperties(model);

      if (curProperties.isEmpty()) {
        return status;
      }
    } catch (final Exception e) {
      this.getLogger().warn(e.toString());
      status = 1;
      return status;
    }

    final Properties properties = new Properties();

    try (Writer writer = this.getWriter(model)) {
      properties.store(writer, comment);
      status = 2;
    } catch (final Exception e) {
      this.getLogger().warn(e.toString());
      status = 1;
    }

    return status;
  }
}
