package java_itamae.domain.service.file;

import java_itamae.domain.component.file.FileComponent;
import java_itamae.domain.component.group.GroupComponent;
import java_itamae.domain.component.mode.ModeComponent;
import java_itamae.domain.component.owner.OwnerComponent;
import java_itamae.domain.model.file.FileResourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {
  @Autowired private FileComponent fc;
  @Autowired private OwnerComponent oc;
  @Autowired private GroupComponent gc;
  @Autowired private ModeComponent mc;

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
