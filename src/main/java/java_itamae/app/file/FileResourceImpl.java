package java_itamae.app.file;

import jakarta.inject.Inject;
import java.util.Map;
import java_itamae.app.common.BaseResource;
import java_itamae.domain.model.file.FileResourceModel;
import java_itamae.domain.model.status.Status;
import java_itamae.domain.service.file.FileService;

/** ファイルの操作を管理する。 */
public class FileResourceImpl implements BaseResource<FileResourceModel> {
  /** {@link FileService} のインスタンス */
  @Inject private transient FileService service;

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
  public Status apply(final Map<String, String> properties) {
    Status status = Status.INIT;
    final FileResourceModel model = this.convertToModel(properties);

    if (this.validate(model)) {
      try {
        if ("create".equals(model.getAction())) {
          status = service.create(model);
        } else if ("delete".equals(model.getAction())) {
          status = service.delete(model);
        }
      } catch (Exception e) {
        final String message = e.toString();
        this.getLogger().warn("{}", message);
        status = Status.ERROR;
      }
    } else {
      status = Status.ERROR;
    }

    return status;
  }
}
