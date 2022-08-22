package java_itamae.domain.service.contents;

import java.util.List;
import java_itamae.domain.component.contents.ContentsComponent;
import java_itamae.domain.model.contents.ContentsModel;
import javax.inject.Inject;

public class ContentsServiceImpl implements ContentsService {
  private ContentsModel model;
  @Inject private ContentsComponent component;

  @Override
  public void init(ContentsModel model) {
    this.model = model;
  }

  @Override
  public List<String> getContents() throws Exception {
    return component.getContents(this.model);
  }

  @Override
  public int updateContents(List<String> contents) {
    final int status = component.updateContents(this.model, contents);
    return status;
  }

  @Override
  public int deleteContents() {
    final int status = component.deleteContents(this.model);
    return status;
  }
}
