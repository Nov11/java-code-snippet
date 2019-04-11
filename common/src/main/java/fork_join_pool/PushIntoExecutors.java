package fork_join_pool;

import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class PushIntoExecutors {
    private static final Logger logger = getLogger(PushIntoExecutors.class);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
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
            CompletableFuture.runAsync(runnable, executorService);
        }

        executorService.awaitTermination(2000, TimeUnit.SECONDS);
    }
}
