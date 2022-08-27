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
  private final FileComponent fc;
  private final OwnerComponent oc;
  private final GroupComponent gc;
  private final ModeComponent mc;

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
    fc = new FileComponentImpl();
    oc = new OwnerComponentImpl();
    gc = new GroupComponentImpl();
    mc = new ModeComponentImpl();
  }

  @Override
  public int create(FileResourceModel model) {
    int status = 0;

    try {
      status = fc.create(model.getPath());

      if (status == 1) {
        return status;
      }

      if (model.getOwner() != null) {
        status = oc.updateOwner(model.getPath(), model.getOwner());

        if (status == 1) {
          return status;
        }
      }

      if (model.getGroup() != null) {
        status = gc.updateGroup(model.getPath(), model.getGroup());

        if (status == 1) {
          return status;
        }
      }

      if (model.getMode() != null) {
        status = mc.updateMode(model.getPath(), model.getMode());

        if (status == 1) {
          return status;
        }
      }
    } catch (final Exception e) {
      this.getLogger().warn(e.toString());
      status = 1;
    }

    return status;
  }

  @Override
  public int delete(FileResourceModel model) {
    int status = 0;

    try {
      status = fc.delete(model.getPath());
    } catch (final Exception e) {
      this.getLogger().warn(e.toString());
      status = 1;
    }

    return status;
  }
}
