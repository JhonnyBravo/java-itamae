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
  public int create(final DirectoryResourceModel model) {
    int status = 0;

    try {
      status = directory.create(model.getPath(), model.isRecursive());

      if (status != 1 && model.getOwner() != null) {
        status = owner.updateOwner(model.getPath(), model.getOwner());
      }

      if (status != 1 && model.getGroup() != null) {
        status = group.updateGroup(model.getPath(), model.getGroup());
      }

      if (status != 1 && model.getMode() != null) {
        status = mode.updateMode(model.getPath(), model.getMode());
      }
    } catch (final Exception e) {
      this.getLogger().warn(e.toString());
      status = 1;
    }

    return status;
  }

  @SuppressWarnings("unused")
  @Override
  public int delete(final DirectoryResourceModel model) {
    int status = 0;

    try {
      status = directory.delete(model.getPath(), model.isRecursive());
    } catch (final Exception e) {
      this.getLogger().warn(e.toString());
      status = 1;
    }

    return status;
  }
}
