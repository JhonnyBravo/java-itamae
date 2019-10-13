package java_itamae.domain.repository.file;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileRepositoryImpl implements FileRepository {
    private final Logger logger;

    public FileRepositoryImpl() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public boolean create(String path) throws Exception {
        boolean status = false;
        final Path p = FileSystems.getDefault().getPath(path);

        if (!p.toFile().isFile()) {
            logger.info(path + " を作成しています......");
            Files.createFile(p);
            status = true;
        }

        return status;
    }

    @Override
    public boolean delete(String path) throws Exception {
        boolean status = false;
        final Path p = FileSystems.getDefault().getPath(path);

        if (p.toFile().isFile()) {
            logger.info(path + " を削除しています......");
            Files.delete(p);
            status = true;
        }

        return status;
    }
}
