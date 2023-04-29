package java_itamae.domain.service.directory;

import java_itamae.domain.component.directory.DirectoryComponent;
import java_itamae.domain.component.directory.DirectoryComponentImpl;
import java_itamae.domain.component.group.GroupComponent;
import java_itamae.domain.component.group.GroupComponentImpl;
import java_itamae.domain.component.mode.ModeComponent;
import java_itamae.domain.component.mode.ModeComponentImpl;
import java_itamae.domain.component.owner.OwnerComponent;
import java_itamae.domain.component.owner.OwnerComponentImpl;
import java_itamae.domain.model.directory.DirectoryResourceModel;
import java_itamae.domain.model.status.Status;

public class DirectoryServiceImpl implements DirectoryService {
  /** {@link DirectoryComponent} */
  private final transient DirectoryComponent directory;

  /** {@link OwnerComponent} */
  private final transient OwnerComponent owner;

  /** {@link GroupComponent} */
  private final transient GroupComponent group;

  /** {@link ModeComponent} */
  private final transient ModeComponent mode;

  /**
   * 初期化処理として以下のインスタンス生成処理を実行する。
   *
   * <ul>
   *   <li>{@link DirectoryComponent}
   *   <li>{@link OwnerComponent}
   *   <li>{@link GroupComponent}
   *   <li>{@link ModeComponent}
   * </ul>
   */
  public DirectoryServiceImpl() {
    directory = new DirectoryComponentImpl();
    owner = new OwnerComponentImpl();
    group = new GroupComponentImpl();
    mode = new ModeComponentImpl();
  }

  @SuppressWarnings("unused")
  @Override
  public Status create(final DirectoryResourceModel model) throws Exception {
    Status status = Status.INIT;

    status = directory.create(model.getPath(), model.isRecursive());

    if (status != Status.ERROR && model.getOwner() != null) {
      status = owner.updateOwner(model.getPath(), model.getOwner());
    }

    if (status != Status.ERROR && model.getGroup() != null) {
      status = group.updateGroup(model.getPath(), model.getGroup());
    }

    if (status != Status.ERROR && model.getMode() != null) {
      status = mode.updateMode(model.getPath(), model.getMode());
    }

    return status;
  }

  @SuppressWarnings("unused")
  @Override
  public Status delete(final DirectoryResourceModel model) throws Exception {
    return directory.delete(model.getPath(), model.isRecursive());
  }
}
