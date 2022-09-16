package java_itamae.app.file;

import java.util.Map;
import java_itamae.app.common.BaseResource;
import java_itamae.domain.model.file.FileResourceModel;
import java_itamae.domain.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** ファイルの操作を管理する。 */
@Service
public class FileResourceImpl implements BaseResource<FileResourceModel> {
  /** {@link FileService} のインスタンス */
  @Autowired private transient FileService service;

  /**
   * プロパティ群を収めた {@link Map} を {@link FileResourceModel} に変換して返す。
   *
   * @param properties プロパティ群を収めた {@link Map} を指定する。
   * @return model {@link Map} から変換された {@link FileResourceModel} を返す。
   */
  @Override
  public FileResourceModel convertToModel(final Map<String, String> properties) {
    final FileResourceModel model = new FileResourceModel();

    model.setAction(properties.get("action"));
    model.setPath(properties.get("path"));
    model.setGroup(properties.get("group"));
    model.setMode(properties.get("mode"));
    model.setOwner(properties.get("owner"));

    return model;
  }

  @Override
  public int apply(final Map<String, String> properties) {
    int status = 0;
    final FileResourceModel model = this.convertToModel(properties);

    if (this.validate(model)) {
      if ("create".equals(model.getAction())) {
        status = service.create(model);
      } else if ("delete".equals(model.getAction())) {
        status = service.delete(model);
      }
    } else {
      status = 1;
    }

    return status;
  }
}
