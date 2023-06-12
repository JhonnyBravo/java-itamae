package java_itamae.domain.service.file;

import java_itamae.domain.component.file.FileComponent;
import java_itamae.domain.component.group.GroupComponent;
import java_itamae.domain.component.mode.ModeComponent;
import java_itamae.domain.component.owner.OwnerComponent;
import java_itamae.domain.model.file.FileResourceModel;
import java_itamae.domain.model.status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {
  /** {@link FileComponent} のインスタンス */
  @Autowired private transient FileComponent file;
  /** {@link OwnerComponent} のインスタンス */
  @Autowired private transient OwnerComponent owner;
  /** {@link GroupComponent} のインスタンス */
  @Autowired private transient GroupComponent group;
  /** {@link ModeComponent} のインスタンス */
  @Autowired private transient ModeComponent mode;

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
