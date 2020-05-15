package file_nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;
import java.time.Duration;
import java.time.LocalDateTime;

public class APIWalkThrough {
    private static final Logger logger = LoggerFactory.getLogger(APIWalkThrough.class);

    private static String makeString(int i) {
        return String.format("%1$04096x", i);
    }

    /**
     * 1024 * 1024 numbers to string and write to file system
     * 4kB * 1M = 4GB
     * <p>
     * buffered writer PT22.821S
     * unbuffered writer PT26.65S
     * bytebuffers PT26.656S
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Path p1 = Paths.get("output");
        if (Files.exists(p1)) {
            Files.delete(p1);
        }
        logger.info("start");
        long tsStart = System.currentTimeMillis();
        try (SeekableByteChannel writer = Files.newByteChannel(p1, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            for (int i = 0; i < 1024 * 1024; i++) {
                writer.write(ByteBuffer.wrap(makeString(i).getBytes()));
            }
        }
        logger.info("end");
        long tsEnd = System.currentTimeMillis();
        System.out.println(Duration.ofMillis(tsEnd - tsStart).toString());
    }
}
