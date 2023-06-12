package java_itamae.domain.service.contents;

import java.util.List;
import java_itamae.domain.component.contents.ContentsComponent;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentsServiceImpl implements ContentsService {
  /** {@link ContentsModel} のインスタンス */
  private transient ContentsModel model;
  /** {@link ContentsComponent} のインスタンス */
  @Autowired private transient ContentsComponent component;

  @Override
  public void init(final ContentsModel model) {
    this.model = model;
  }

  @Override
  public List<String> getContents() throws Exception {
    return component.getContents(this.model);
  }

  @Override
  public Status updateContents(final List<String> contents) throws Exception {
    return component.updateContents(this.model, contents);
  }

  @Override
  public Status deleteContents() throws Exception {
    return component.deleteContents(this.model);
  }
}
