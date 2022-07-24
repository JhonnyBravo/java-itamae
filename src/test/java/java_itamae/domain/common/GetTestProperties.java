package java_itamae.domain.common;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GetTestProperties implements Supplier<Map<String, String>> {

  @Override
  public Map<String, String> get() {
    final Map<String, String> properties = new HashMap<>();
    properties.put("property1", "1 つ目のプロパティ");
    properties.put("property2", "2 つ目のプロパティ");

    return properties;
  }
}
