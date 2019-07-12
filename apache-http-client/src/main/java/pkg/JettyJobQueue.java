package pkg;

import org.eclipse.jetty.util.BlockingArrayQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyJobQueue {
    private static final Logger logger = LoggerFactory.getLogger(JettyJobQueue.class);

    public static void main(String[] args) {
        BlockingArrayQueue<Long> blockingArrayQueue = new BlockingArrayQueue<>(8, 8);

        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            boolean ret = blockingArrayQueue.offer(i);
            if (!ret) {
                logger.info("failed inserting i:{}", i);
            }
        }

    }
}
