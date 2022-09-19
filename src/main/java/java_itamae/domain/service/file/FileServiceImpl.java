package java_itamae.domain.service.file;

import jakarta.inject.Inject;
import java_itamae.domain.component.file.FileComponent;
import java_itamae.domain.component.group.GroupComponent;
import java_itamae.domain.component.mode.ModeComponent;
import java_itamae.domain.component.owner.OwnerComponent;
import java_itamae.domain.model.file.FileResourceModel;

public class FileServiceImpl implements FileService {
  /** {@link FileComponent} のインスタンス */
  @Inject private transient FileComponent file;
  /** {@link OwnerComponent} のインスタンス */
  @Inject private transient OwnerComponent owner;
  /** {@link GroupComponent} のインスタンス */
  @Inject private transient GroupComponent group;
  /** {@link ModeComponent} のインスタンス */
  @Inject private transient ModeComponent mode;

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
