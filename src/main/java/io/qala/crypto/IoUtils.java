package io.qala.crypto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IoUtils {
    public static byte[] readFully(InputStream is) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];

        while (true) {
            try {
                if ((nRead = is.read(data, 0, data.length)) == -1) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }
    public static byte[] readFromFile(Path filePath) {
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
