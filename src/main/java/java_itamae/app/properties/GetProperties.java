package java_itamae.app.properties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.service.properties.PropertiesService;
import java_itamae.domain.service.properties.PropertiesServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** プロパティファイルのからキーと値を読み込む。 */
public class GetProperties implements Function<ContentsModel, Map<String, String>> {

  /**
   * プロパティファイルからキーと値を読込み、 {@link Map} 変換して返す。
   *
   * @param model 読込み対象とするプロパティファイルの情報を収めた {@link ContentsModel} を指定する。
   * @return properties キーと値を収めた {@link Map}
   */
  @Override
  public Map<String, String> apply(final ContentsModel model) {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    Map<String, String> properties = new ConcurrentHashMap<>();

    try {
      final PropertiesService service = new PropertiesServiceImpl();
      service.init(model);
      properties = service.getProperties();
    } catch (final Exception e) {
      final String message = e.toString();
      logger.warn("{}", message);
    }

    return properties;
  }
}
