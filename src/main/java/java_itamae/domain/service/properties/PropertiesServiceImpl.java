package java_itamae.domain.service.properties;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java_itamae.domain.component.properties.PropertiesComponent;
import java_itamae.domain.component.properties.PropertiesComponentImpl;
import java_itamae.domain.model.contents.ContentsModel;

public class PropertiesServiceImpl implements PropertiesService {
  /** {@link ContentsModel} */
  private transient ContentsModel model;

  /** {@link PropertiesComponent} */
  private final transient PropertiesComponent component;

  /** 初期化処理として {@link PropertiesComponent} のインスタンス生成を実行する。 */
  public PropertiesServiceImpl() {
    component = new PropertiesComponentImpl();
  }

  @Override
  public void init(final ContentsModel model) {
    this.model = model;
  }

  @Override
  public Map<String, String> getProperties() throws Exception {
    return component.getProperties(this.model);
  }

  @Override
  public String getProperty(final String key) throws Exception {
    final Map<String, String> properties = component.getProperties(this.model);

    if (!properties.containsKey(key)) {
      throw new Exception(key + " が見つかりません。");
    }

    return properties.get(key);
  }

  @Override
  public int createProperty(final String key, final String value) {
    int status = 0;

    Map<String, String> properties = new ConcurrentHashMap<>();

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

    if (status != 1) {
      properties.put(key, value);
      final String fileName = new File(model.getPath()).getName();
      status = component.updateProperties(model, properties, fileName);
    }

    return status;
  }

  @Override
  public int updateProperty(final String key, final String value) {
    int status = 0;
    Map<String, String> properties = new ConcurrentHashMap<>();

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

    if (status != 1 && !value.equals(properties.get(key))) {
      final String fileName = new File(model.getPath()).getName();
      properties.put(key, value);
      status = component.updateProperties(model, properties, fileName);
    }

    return status;
  }

  @Override
  public int deleteProperty(final String key) {
    int status = 0;
    Map<String, String> properties = new ConcurrentHashMap<>();

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

    if (status != 1) {
      final String fileName = new File(model.getPath()).getName();
      status = component.updateProperties(model, properties, fileName);
    }

    return status;
  }
}
