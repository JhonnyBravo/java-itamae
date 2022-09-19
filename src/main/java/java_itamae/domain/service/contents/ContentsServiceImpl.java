package java_itamae.domain.service.contents;

import jakarta.inject.Inject;
import java.util.List;
import java_itamae.domain.component.contents.ContentsComponent;
import java_itamae.domain.model.contents.ContentsModel;

public class ContentsServiceImpl implements ContentsService {
  /** {@link ContentsModel} のインスタンス */
  private transient ContentsModel model;
  /** {@link ContentsComponent} のインスタンス */
  @Inject private transient ContentsComponent component;

  @Override
  public void init(final ContentsModel model) {
    this.model = model;
  }

  @Override
  public List<String> getContents() throws Exception {
    return component.getContents(this.model);
  }

  @Override
  public int updateContents(final List<String> contents) {
    return component.updateContents(this.model, contents);
  }

  @Override
  public int deleteContents() {
    return component.deleteContents(this.model);
  }
}
