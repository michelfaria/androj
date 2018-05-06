package component.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class TempFilesImpl implements TempFiles {

    private static final Logger LOGGER = LoggerFactory.getLogger(TempFilesImpl.class);
    private File tempDir;

    public TempFilesImpl() {
    }

    @PostConstruct
    public void init() throws IOException {
        tempDir = Files.createTempDirectory("androj").toFile();
        LOGGER.info("Created temporary directory: " + tempDir);
    }

    @Override
    public File create(byte[] content) throws IOException {
        File file = File.createTempFile("temp", ".tmp", tempDir);
        Files.write(file.toPath(), content);
        return file;
    }

    @Override
    public Path tempPath() {
        return Paths.get(
                tempDir.getPath(),
                UUID.randomUUID()
                        .toString()
                        .replaceAll("-", ""));
    }
}
