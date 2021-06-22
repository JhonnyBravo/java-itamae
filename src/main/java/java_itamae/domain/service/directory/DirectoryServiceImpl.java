package java_itamae.domain.service.directory;

import java_itamae.domain.model.Attribute;
import java_itamae.domain.repository.directory.DirectoryRepository;
import java_itamae.domain.repository.directory.DirectoryRepositoryImpl;
import java_itamae.domain.repository.group.GroupRepository;
import java_itamae.domain.repository.group.GroupRepositoryImpl;
import java_itamae.domain.repository.mode.ModeRepository;
import java_itamae.domain.repository.mode.ModeRepositoryImpl;
import java_itamae.domain.repository.owner.OwnerRepository;
import java_itamae.domain.repository.owner.OwnerRepositoryImpl;
import javax.enterprise.inject.New;
import javax.inject.Inject;

public class DirectoryServiceImpl implements DirectoryService {
  @Inject
  @New(DirectoryRepositoryImpl.class)
  private DirectoryRepository dr;
  @Inject
  @New(OwnerRepositoryImpl.class)
  private OwnerRepository or;
  @Inject
  @New(GroupRepositoryImpl.class)
  private GroupRepository gr;
  @Inject
  @New(ModeRepositoryImpl.class)
  private ModeRepository mr;

  private boolean recursive;

  public DirectoryServiceImpl() {
    recursive = false;
  }

  @Override
  public void setRecursive(boolean recursive) {
    this.recursive = recursive;
  }

  @Override
  public boolean create(Attribute attr) throws Exception {
    boolean status = false;

    if (recursive) {
      status = dr.createRecursive(attr.getPath());
    } else {
      status = dr.create(attr.getPath());
    }

    if (attr.getOwner() != null) {
      status = or.updateOwner(attr.getPath(), attr.getOwner());
    }

    if (attr.getGroup() != null) {
      status = gr.updateGroup(attr.getPath(), attr.getGroup());
    }

    if (attr.getMode() != null) {
      status = mr.updateMode(attr.getPath(), attr.getMode());
    }

    return status;
  }

  @Override
  public boolean delete(Attribute attr) throws Exception {
    boolean status = false;

    if (recursive) {
      status = dr.deleteRecursive(attr.getPath());
    } else {
      status = dr.delete(attr.getPath());
    }

    return status;
  }
}
