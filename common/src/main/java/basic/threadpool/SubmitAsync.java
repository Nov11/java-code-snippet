package basic.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class SubmitAsync {
    private static final Logger logger = LoggerFactory.getLogger(SubmitAsync.class);
    private static ExecutorService executorService = new ThreadPoolExecutor(
            1,
            1,
            60L, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(1),
            new ThreadFactoryBuilder().setNameFormat("name" + "-%d").build(),
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    logger.info("reject");
                }
            });

    private static int runLongLongTime() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(SubmitAsync::runLongLongTime, executorService);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(SubmitAsync::runLongLongTime, executorService);
        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(SubmitAsync::runLongLongTime, executorService);
        future1.thenAccept(i -> {
            logger.info("f1 done");
        });
        future2.thenAccept(i -> {
            logger.info("f2 done");
        });
        future3.thenAccept(i -> {
            logger.info("f3 done");
        });
    }
}
