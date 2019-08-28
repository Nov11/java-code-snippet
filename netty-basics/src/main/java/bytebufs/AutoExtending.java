package bytebufs;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoExtending {
    private static final Logger logger = LoggerFactory.getLogger(AutoExtending.class);

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.buffer(10);
        logger.info("{}", byteBuf);
        for (int i = 0; i < 20; i++) {
            byteBuf.writeByte(i);
        }

        logger.info("{}", byteBuf);
    }
}
