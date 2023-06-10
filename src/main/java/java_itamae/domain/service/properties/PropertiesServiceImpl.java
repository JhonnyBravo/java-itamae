package java_itamae.domain.service.properties;

import java.io.File;
import java.util.Map;
import java_itamae.domain.component.properties.PropertiesComponent;
import java_itamae.domain.component.properties.PropertiesComponentImpl;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.status.Status;

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
      throw new IllegalArgumentException(key + " が見つかりません。");
    }

    return properties.get(key);
  }

  @Override
  public Status createProperty(final String key, final String value) throws Exception {
    Status status = Status.INIT;
    Map<String, String> properties = component.getProperties(model);

    if (properties.containsKey(key)) {
      throw new IllegalArgumentException(key + " は登録済みです。");
    }

    properties.put(key, value);
    final String fileName = new File(model.getPath()).getName();
    status = component.updateProperties(model, properties, fileName);

    return status;
  }

  @Override
  public Status updateProperty(final String key, final String value) throws Exception {
    Status status = Status.INIT;
    Map<String, String> properties = component.getProperties(model);

    if (!properties.containsKey(key)) {
      throw new IllegalArgumentException(key + " が見つかりません");
    }

    if (!value.equals(properties.get(key))) {
      final String fileName = new File(model.getPath()).getName();
      properties.put(key, value);
      status = component.updateProperties(model, properties, fileName);
    }

    return status;
  }

  @Override
  public Status deleteProperty(final String key) throws Exception {
    Status status = Status.INIT;
    Map<String, String> properties = component.getProperties(this.model);

    if (!properties.containsKey(key)) {
      throw new IllegalArgumentException(key + " が見つかりません。");
    }

    final String fileName = new File(model.getPath()).getName();
    properties.remove(key);
    status = component.updateProperties(model, properties, fileName);

    return status;
  }
}
