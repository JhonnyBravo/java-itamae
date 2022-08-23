package java_itamae.app.template;

import java.util.List;
import java.util.Map;
import java_itamae.app.common.BaseResource;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.template.TemplateResourceModel;
import java_itamae.domain.service.contents.ContentsService;
import javax.inject.Inject;

public class TemplateResourceImpl implements BaseResource<TemplateResourceModel> {
  @Inject private ContentsService service;

  @Override
  public TemplateResourceModel convertToModel(Map<String, String> properties) {
    final TemplateResourceModel model = new TemplateResourceModel();

    model.setAction(properties.get("action"));
    model.setPath(properties.get("path"));
    model.setSource(properties.get("source"));
    model.setEncoding(properties.get("encoding"));

    return model;
  }

  private List<String> getSourceContents(TemplateResourceModel model) throws Exception {
    final ContentsModel sourceModel = new ContentsModel();

    if (model.getAction().equals("delete")) {
      sourceModel.setPath(model.getPath());
    } else {
      sourceModel.setPath(model.getSource());
    }

    sourceModel.setEncoding(model.getEncoding());

    service.init(sourceModel);
    return service.getContents();
  }

  private int updateContents(TemplateResourceModel model, List<String> contents) {
    int status = 0;

    final ContentsModel targetModel = new ContentsModel();
    targetModel.setPath(model.getPath());
    targetModel.setEncoding(model.getEncoding());

    service.init(targetModel);

    if (model.getAction().equals("create")) {
      status = service.updateContents(contents);
    } else if (model.getAction().equals("delete")) {
      status = service.deleteContents();
    }

    return status;
  }

  @Override
  public int apply(Map<String, String> properties) {
    int status = 0;
    final TemplateResourceModel model = this.convertToModel(properties);

    if (!this.validate(model)) {
      status = 1;
      return status;
    }

    try {
      final List<String> sourceContents = this.getSourceContents(model);
      status = this.updateContents(model, sourceContents);
    } catch (final Exception e) {
      this.getLogger().warn(e.toString());
      status = 1;
    }

    return status;
  }
}
