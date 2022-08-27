package java_itamae.app.directory;

import java.util.Map;
import java_itamae.app.common.BaseResource;
import java_itamae.domain.model.directory.DirectoryResourceModel;
import java_itamae.domain.service.directory.DirectoryService;
import java_itamae.domain.service.directory.DirectoryServiceImpl;

/** ディレクトリの操作を管理する。 */
public class DirectoryResourceImpl implements BaseResource<DirectoryResourceModel> {
  /**
   * プロパティ群を収めた {@link Map} を {@link DirectoryResourceModel} に変換して返す。
   *
   * @param properties プロパティ群を収めた {@link Map} を指定する。
   * @return model {@link Map} から変換された {@link DirectoryResourceModel} を返す。
   */
  @Override
  public DirectoryResourceModel convertToModel(Map<String, String> properties) {
    final DirectoryResourceModel model = new DirectoryResourceModel();

    model.setAction(properties.get("action"));
    model.setPath(properties.get("path"));
    model.setRecursive(properties.get("recursive"));
    model.setGroup(properties.get("group"));
    model.setMode(properties.get("mode"));
    model.setOwner(properties.get("owner"));

    return model;
  }

  @Override
  public int apply(Map<String, String> properties) {
    int status = 0;
    final DirectoryResourceModel model = this.convertToModel(properties);

    if (!this.validate(model)) {
      status = 1;
      return status;
    }

    final DirectoryService service = new DirectoryServiceImpl();

    if ("create".equals(model.getAction())) {
      status = service.create(model);
    } else if ("delete".equals(model.getAction())) {
      status = service.delete(model);
    }

    return status;
  }
}
