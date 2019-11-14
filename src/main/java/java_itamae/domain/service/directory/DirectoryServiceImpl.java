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

public class DirectoryServiceImpl implements DirectoryService {
    private boolean recursive;
    private final DirectoryRepository dr;
    private final OwnerRepository or;
    private final GroupRepository gr;
    private final ModeRepository mr;

    public DirectoryServiceImpl() {
        recursive = false;
        dr = new DirectoryRepositoryImpl();
        or = new OwnerRepositoryImpl();
        gr = new GroupRepositoryImpl();
        mr = new ModeRepositoryImpl();
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
            status = or.setOwner(attr.getPath(), attr.getOwner());
        }

        if (attr.getGroup() != null) {
            status = gr.setGroup(attr.getPath(), attr.getGroup());
        }

        if (attr.getMode() != null) {
            status = mr.setMode(attr.getPath(), attr.getMode());
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
