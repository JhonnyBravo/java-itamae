package java_itamae.domain.service.properties;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java_itamae.domain.component.properties.PropertiesComponent;
import java_itamae.domain.model.contents.ContentsModel;
import javax.inject.Inject;

public class PropertiesServiceImpl implements PropertiesService {
  private ContentsModel model;
  @Inject private PropertiesComponent component;

  @Override
  public void init(ContentsModel model) {
    this.model = model;
  }

  @Override
  public Map<String, String> getProperties() throws Exception {
    return component.getProperties(this.model);
  }

  @Override
  public String getProperty(String key) throws Exception {
    final Map<String, String> properties = component.getProperties(this.model);

    if (!properties.containsKey(key)) {
      throw new Exception(key + " が見つかりません。");
    }

    return properties.get(key);
  }

  @Override
  public int createProperty(String key, String value) {
    int status = 0;

    Map<String, String> properties = new HashMap<>();

    try {
      properties = component.getProperties(model);

      if (properties.containsKey(key)) {
        this.getLogger().warn("{} は登録済みです。", key);
        status = 1;
      }
    } catch (final Exception e) {
      this.getLogger().warn(e.toString());
      status = 1;
    }

    if (status == 1) {
      return status;
    }

    properties.put(key, value);
    final String fileName = new File(model.getPath()).getName();
    status = component.updateProperties(model, properties, fileName);

    return status;
  }

  @Override
  public int updateProperty(String key, String value) {
    int status = 0;
    Map<String, String> properties = new HashMap<>();

    try {
      properties = component.getProperties(model);

      if (!properties.containsKey(key)) {
        this.getLogger().warn("{} が見つかりません。", key);
        status = 1;
      }
    } catch (final Exception e) {
      this.getLogger().warn(e.toString());
      status = 1;
    }

    if (status == 1) {
      return status;
    }

    if (!value.equals(properties.get(key))) {
      final String fileName = new File(model.getPath()).getName();
      properties.put(key, value);
      status = component.updateProperties(model, properties, fileName);
    }

    return status;
  }

  @Override
  public int deleteProperty(String key) {
    int status = 0;
    Map<String, String> properties = new HashMap<>();

    try {
      properties = component.getProperties(this.model);

      if (!properties.containsKey(key)) {
        this.getLogger().warn("{} が見つかりません。", key);
        status = 1;
      }
    } catch (final Exception e) {
      this.getLogger().warn(e.toString());
      status = 1;
    }

    if (status == 1) {
      return status;
    }

    final String fileName = new File(model.getPath()).getName();
    status = component.updateProperties(model, properties, fileName);

    return status;
  }
}
