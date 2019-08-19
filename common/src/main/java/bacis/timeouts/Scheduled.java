package bacis.timeouts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduled {
    private static final Logger logger = LoggerFactory.getLogger(Scheduled.class);

    private static class T implements Runnable{
        public T() {
            logger.info("ctor");
        }

        @Override
        public void run() {
            logger.info("done");
        }
    }

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(new T(), 100, TimeUnit.MILLISECONDS);
        logger.info("main exits");
    }
}
