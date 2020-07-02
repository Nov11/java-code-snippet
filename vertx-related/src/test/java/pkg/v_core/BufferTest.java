package pkg.v_core;

import io.vertx.core.buffer.Buffer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BufferTest {
    private static final Logger logger = LoggerFactory.getLogger(BufferTest.class);
    @Test
    public void set() {
        Buffer buff = Buffer.buffer();

        buff.setInt(1000, 123);
        buff.setString(0, "hello");

        logger.info("size : {}", buff.length());
    }
}
