package java_itamae.domain.service.directory;

import jakarta.inject.Inject;
import java_itamae.domain.component.directory.DirectoryComponent;
import java_itamae.domain.component.group.GroupComponent;
import java_itamae.domain.component.mode.ModeComponent;
import java_itamae.domain.component.owner.OwnerComponent;
import java_itamae.domain.model.directory.DirectoryResourceModel;

public class DirectoryServiceImpl implements DirectoryService {
  @Inject private DirectoryComponent dc;
  @Inject private OwnerComponent oc;
  @Inject private GroupComponent gc;
  @Inject private ModeComponent mc;

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
