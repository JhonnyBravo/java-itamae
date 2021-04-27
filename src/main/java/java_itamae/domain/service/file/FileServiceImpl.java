package java_itamae.domain.service.file;

import java_itamae.domain.model.Attribute;
import java_itamae.domain.repository.file.FileRepository;
import java_itamae.domain.repository.file.FileRepositoryImpl;
import java_itamae.domain.repository.group.GroupRepository;
import java_itamae.domain.repository.group.GroupRepositoryImpl;
import java_itamae.domain.repository.mode.ModeRepository;
import java_itamae.domain.repository.mode.ModeRepositoryImpl;
import java_itamae.domain.repository.owner.OwnerRepository;
import java_itamae.domain.repository.owner.OwnerRepositoryImpl;

public class FileServiceImpl implements FileService {
  private final FileRepository fr;
  private final OwnerRepository or;
  private final GroupRepository gr;
  private final ModeRepository mr;

  public FileServiceImpl() {
    fr = new FileRepositoryImpl();
    or = new OwnerRepositoryImpl();
    gr = new GroupRepositoryImpl();
    mr = new ModeRepositoryImpl();
  }

  @Override
  public boolean create(Attribute attr) throws Exception {
    boolean status = fr.create(attr.getPath());

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
    return fr.delete(attr.getPath());
  }
}
