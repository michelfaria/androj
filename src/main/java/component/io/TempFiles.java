package component.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;

public interface TempFiles {

    default File createDecodeBase64(String content) throws IOException {
        return create(Base64
                .getDecoder()
                .decode(content));
    }

    default File create(String content) throws IOException {
        return create(content.getBytes());
    }

    File create(byte[] content) throws IOException;

    Path tempPath();
}
