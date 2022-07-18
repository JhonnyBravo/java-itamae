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
  private final DirectoryComponent dc;
  private final OwnerComponent oc;
  private final GroupComponent gc;
  private final ModeComponent mc;

  public DirectoryServiceImpl() {
    dc = new DirectoryComponentImpl();
    oc = new OwnerComponentImpl();
    gc = new GroupComponentImpl();
    mc = new ModeComponentImpl();
  }

  @Override
  public int create(DirectoryResourceModel model) {
    int status = 0;

    try {
      status = dc.create(model.getPath(), model.isRecursive());

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
  public int delete(DirectoryResourceModel model) {
    int status = 0;

    try {
      status = dc.delete(model.getPath(), model.isRecursive());
    } catch (final Exception e) {
      this.getLogger().warn(e.toString());
      status = 1;
    }

    return status;
  }
}
