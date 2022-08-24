package java_itamae.domain.service.file;

import jakarta.inject.Inject;
import java_itamae.domain.component.file.FileComponent;
import java_itamae.domain.component.group.GroupComponent;
import java_itamae.domain.component.mode.ModeComponent;
import java_itamae.domain.component.owner.OwnerComponent;
import java_itamae.domain.model.file.FileResourceModel;

public class FileServiceImpl implements FileService {
  @Inject private FileComponent fc;
  @Inject private OwnerComponent oc;
  @Inject private GroupComponent gc;
  @Inject private ModeComponent mc;

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
