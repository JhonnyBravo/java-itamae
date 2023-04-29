package java_itamae.domain.service.file;

import java_itamae.domain.component.file.FileComponent;
import java_itamae.domain.component.file.FileComponentImpl;
import java_itamae.domain.component.group.GroupComponent;
import java_itamae.domain.component.group.GroupComponentImpl;
import java_itamae.domain.component.mode.ModeComponent;
import java_itamae.domain.component.mode.ModeComponentImpl;
import java_itamae.domain.component.owner.OwnerComponent;
import java_itamae.domain.component.owner.OwnerComponentImpl;
import java_itamae.domain.model.file.FileResourceModel;
import java_itamae.domain.model.status.Status;

public class FileServiceImpl implements FileService {
  /** {@link FileComponent} */
  private final transient FileComponent file;

  /** {@link OwnerComponent} */
  private final transient OwnerComponent owner;

  /** {@link GroupComponent} */
  private final transient GroupComponent group;

  /** {@link ModeComponent} */
  private final transient ModeComponent mode;

  /**
   * 初期化処理として以下のインスタンス生成を実行する。
   *
   * <ul>
   *   <li>{@link FileComponent}
   *   <li>{@link OwnerComponent}
   *   <li>{@link GroupComponent}
   *   <li>{@link ModeComponent}
   * </ul>
   */
  public FileServiceImpl() {
    file = new FileComponentImpl();
    owner = new OwnerComponentImpl();
    group = new GroupComponentImpl();
    mode = new ModeComponentImpl();
  }

  @SuppressWarnings("unused")
  @Override
  public Status create(final FileResourceModel model) throws Exception {
    Status status = Status.INIT;

    status = file.create(model.getPath());

    if (model.getOwner() != null) {
      status = owner.updateOwner(model.getPath(), model.getOwner());
    }

    if (model.getGroup() != null) {
      status = group.updateGroup(model.getPath(), model.getGroup());
    }

    if (model.getMode() != null) {
      status = mode.updateMode(model.getPath(), model.getMode());
    }

    return status;
  }

  @Override
  public Status delete(final FileResourceModel model) throws Exception {
    return file.delete(model.getPath());
  }
}
