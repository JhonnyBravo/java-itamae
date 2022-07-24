package java_itamae.app.file;

import java.util.Map;
import java_itamae.app.common.BaseResource;
import java_itamae.domain.model.file.FileResourceModel;
import java_itamae.domain.service.file.FileService;
import java_itamae.domain.service.file.FileServiceImpl;

public class FileResourceImpl implements BaseResource<FileResourceModel> {

  @Override
  public FileResourceModel convertToModel(Map<String, String> properties) {
    final FileResourceModel model = new FileResourceModel();

    model.setAction(properties.get("action"));
    model.setPath(properties.get("path"));
    model.setGroup(properties.get("group"));
    model.setMode(properties.get("mode"));
    model.setOwner(properties.get("owner"));

    return model;
  }

  @Override
  public int apply(Map<String, String> properties) {
    int status = 0;
    final FileResourceModel model = this.convertToModel(properties);

    if (!this.validate(model)) {
      status = 1;
      return status;
    }

    final FileService service = new FileServiceImpl();

    if (model.getAction().equals("create")) {
      status = service.create(model);
    } else if (model.getAction().equals("delete")) {
      status = service.delete(model);
    }

    return status;
  }
}
