package component.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class TempFilesImpl implements TempFiles {

    private static final Logger LOGGER = LoggerFactory.getLogger(TempFilesImpl.class);
    private static final File TEMP_DIR;

    static {
        try {
            TEMP_DIR = Files.createTempDirectory("androj").toFile();
            LOGGER.info("Created temporary directory: " + TEMP_DIR);
        } catch (IOException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public TempFilesImpl() {
    }

    @Override
    public File create(byte[] content) throws IOException {
        File file = File.createTempFile("magik", ".tmp", TEMP_DIR);
        Files.write(file.toPath(), content);
        return file;
    }
}
