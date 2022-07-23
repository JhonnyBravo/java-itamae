package java_itamae.app.directory;

import java.util.Map;

import java_itamae.app.common.BaseResource;
import java_itamae.domain.model.directory.DirectoryResourceModel;
import java_itamae.domain.service.directory.DirectoryService;
import java_itamae.domain.service.directory.DirectoryServiceImpl;

public class DirectoryResourceImpl implements BaseResource<DirectoryResourceModel> {

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

    if (model.getAction().equals("create")) {
      status = service.create(model);
    } else if (model.getAction().equals("delete")) {
      status = service.delete(model);
    }

    return status;
  }
}
