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
  public int create(final FileResourceModel model) {
    int status = 0;

    try {
      status = file.create(model.getPath());

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
  public int delete(final FileResourceModel model) {
    int status = 0;

    try {
      status = file.delete(model.getPath());
    } catch (final Exception e) {
      this.getLogger().warn(e.toString());
      status = 1;
    }

    return status;
  }
}
