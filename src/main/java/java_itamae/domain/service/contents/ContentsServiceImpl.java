package java_itamae.domain.service.contents;

import java.util.List;
import java_itamae.domain.component.contents.ContentsComponent;
import java_itamae.domain.component.contents.ContentsComponentImpl;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.status.Status;

public class ContentsServiceImpl implements ContentsService {
  /** {@link ContentsModel} */
  private transient ContentsModel model;
  /** {@link ContentsComponent} */
  private final transient ContentsComponent component;

  /** 初期化処理を実行する。 */
  public ContentsServiceImpl() {
    component = new ContentsComponentImpl();
  }

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
