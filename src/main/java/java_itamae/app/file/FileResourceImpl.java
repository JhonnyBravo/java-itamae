package java_itamae.app.file;

import java.util.Map;
import java_itamae.app.common.BaseResource;
import java_itamae.domain.model.file.FileResourceModel;
import java_itamae.domain.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileResourceImpl implements BaseResource<FileResourceModel> {
  @Autowired private FileService service;

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

    if (model.getAction().equals("create")) {
      status = service.create(model);
    } else if (model.getAction().equals("delete")) {
      status = service.delete(model);
    }

    return status;
  }
}
