package java_itamae.domain.repository.directory;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectoryRepositoryImpl implements DirectoryRepository {
    private final Logger logger;

    public DirectoryRepositoryImpl() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public boolean create(String path) throws Exception {
        boolean status = false;
        final Path p = FileSystems.getDefault().getPath(path);

        if (!p.toFile().isDirectory()) {
            logger.info(path + " を作成しています......");
            Files.createDirectory(p);
            status = true;
        }

        return status;
    }

    @Override
    public boolean createRecursive(String path) throws Exception {
        boolean status = false;
        final Path p = FileSystems.getDefault().getPath(path);

        if (!p.toFile().isDirectory()) {
            logger.info(path + " を作成しています......");
            Files.createDirectories(p);
            status = true;
        }

        return status;
    }

    @Override
    public boolean delete(String path) throws Exception {
        boolean status = false;
        final Path p = FileSystems.getDefault().getPath(path);

        if (p.toFile().isDirectory()) {
            logger.info(path + " を削除しています......");
            Files.delete(p);
            status = true;
        }

        return status;
    }

    @Override
    public boolean deleteRecursive(String path) throws Exception {
        boolean status = false;
        final File directory = new File(path);

        if (directory.isDirectory()) {
            for (final File file : directory.listFiles()) {
                if (file.isDirectory()) {
                    deleteRecursive(file.getPath());
                } else {
                    logger.info(file.getPath() + " を削除しています......");
                    file.delete();
                }
            }

            logger.info(path + " を削除しています......");
            status = directory.delete();
        }

        return status;
    }
}
