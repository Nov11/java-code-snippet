package fork_join_pool;

import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * runnable can be execute in main thread
 * in case it is a blocking call, you'll never want it to happen
 * this does block the whole progress
 */
public class PushIntoIt {
    private static final Logger logger = getLogger(PushIntoIt.class);

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(20);
        Runnable runnable = () -> {
            logger.info("start");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("end");
        };
        for (int i = 0; i < 10; i++) {
            CompletableFuture.runAsync(runnable, forkJoinPool);
        }

        forkJoinPool.awaitQuiescence(2000, TimeUnit.SECONDS);
    }
}
