package basic.memoryleak.jemalloc;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class NoLeaking {
    //    private static final Logger logger = LoggerFactory.getLogger(NoLeaking.class);
    public static void main(String[] args) throws InterruptedException {
//        logger.info("{}", System.getenv("LD_PRELOAD"));
        System.out.println(System.getenv("LD_PRELOAD"));
        List<ByteBuffer> list = new ArrayList();
        for (long i = 0; i < 10; i++) {
            ByteBuffer directBuffer = ByteBuffer.allocateDirect(1024 * 1024);
            list.add(directBuffer);
        }
    }
}
