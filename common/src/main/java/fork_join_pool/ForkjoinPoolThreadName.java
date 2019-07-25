package fork_join_pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class ForkjoinPoolThreadName {
    private static final Logger logger = LoggerFactory.getLogger(ForkjoinPoolThreadName.class);
    static class TT extends ForkJoinWorkerThread {

        /**
         * Creates a ForkJoinWorkerThread operating in the given pool.
         *
         * @param pool the pool this thread works in
         * @throws NullPointerException if pool is null
         */
        protected TT(ForkJoinPool pool, String name) {
            super(pool);
            setName(name);
        }
    }
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                logger.info("tname: {}", Thread.currentThread().getName());
            }
        };
        ForkJoinPool forkJoinPool = new ForkJoinPool(2, new ForkJoinPool.ForkJoinWorkerThreadFactory() {
            @Override
            public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
                return new TT(pool, "blabla");
            }
        }, null, true);
        forkJoinPool.execute(runnable);
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        commonPool.execute(runnable);
        forkJoinPool.awaitQuiescence(10, TimeUnit.SECONDS);

        Executor executor = Executors.newWorkStealingPool();
        executor.execute(runnable);
    }
}
