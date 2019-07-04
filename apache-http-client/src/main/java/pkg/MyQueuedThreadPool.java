package pkg;

import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;

public class MyQueuedThreadPool extends QueuedThreadPool {
    private static Logger logger = LoggerFactory.getLogger(MyQueuedThreadPool.class);

    private void setLowThreadThreshold(int maxThreads) {
        int threshold = Math.max(1, (int) (maxThreads * 0.2));
        super.setLowThreadsThreshold(threshold);
    }

    public MyQueuedThreadPool(int maxThreads, int minThreads, int idleTime, BlockingQueue<Runnable> blockingQueue) {
        super(maxThreads, minThreads, idleTime, blockingQueue);
        setLowThreadThreshold(maxThreads);
    }

    @Override
    public void execute(Runnable job) {
        try {
            super.execute(job);
        } catch (RejectedExecutionException rejectException) {
            logger.error("QTP status:{}", this);
            throw rejectException;
        }
    }
}
