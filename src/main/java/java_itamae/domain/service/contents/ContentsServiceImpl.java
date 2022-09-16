package java_itamae.domain.service.contents;

import java.util.List;
import java_itamae.domain.component.contents.ContentsComponent;
import java_itamae.domain.model.contents.ContentsModel;
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
  public int updateContents(final List<String> contents) {
    return component.updateContents(this.model, contents);
  }

  @Override
  public int deleteContents() {
    return component.deleteContents(this.model);
  }
}
