package java_itamae.domain.service.directory;

import java_itamae.domain.component.directory.DirectoryComponent;
import java_itamae.domain.component.group.GroupComponent;
import java_itamae.domain.component.mode.ModeComponent;
import java_itamae.domain.component.owner.OwnerComponent;
import java_itamae.domain.model.directory.DirectoryResourceModel;
import java_itamae.domain.model.status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectoryServiceImpl implements DirectoryService {
  /** {@link DirectoryComponent} のインスタンス */
  @Autowired private transient DirectoryComponent directory;
  /** {@link OwnerComponent} のインスタンス */
  @Autowired private transient OwnerComponent owner;
  /** {@link GroupComponent} のインスタンス */
  @Autowired private transient GroupComponent group;
  /** {@link ModeComponent} のインスタンス */
  @Autowired private transient ModeComponent mode;

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
