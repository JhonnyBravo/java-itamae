package java_itamae.domain.service.directory;

import java_itamae.domain.component.directory.DirectoryComponent;
import java_itamae.domain.component.group.GroupComponent;
import java_itamae.domain.component.mode.ModeComponent;
import java_itamae.domain.component.owner.OwnerComponent;
import java_itamae.domain.model.directory.DirectoryResourceModel;
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
