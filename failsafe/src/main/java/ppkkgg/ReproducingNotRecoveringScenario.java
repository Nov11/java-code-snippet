package ppkkgg;

import com.timgroup.statsd.StatsDClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import port.net.jodah.failsafe.CircuitBreaker;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReproducingNotRecoveringScenario {
    private static final Logger logger = LoggerFactory.getLogger(ReproducingNotRecoveringScenario.class);
    private static final StatsDClient statsDClient = TimStatsDClient.getClient();
    private static CircuitBreaker circuitBreaker = new CircuitBreaker()
            .withDelay(1, TimeUnit.SECONDS)
            .withFailureThreshold(3, 5)
            .withSuccessThreshold(1, 1);
    private static boolean suc = false;

    private static boolean allow() {
        if (circuitBreaker.allowsExecution()) {
            statsDClient.increment("suc");
            logger.info("allow");
            return true;
        }
        statsDClient.increment("fail");
        logger.info("reject");
        return false;
    }

    private static void work() throws InvocationTargetException, IllegalAccessException {
        if (!allow()) {
            return;
        }

        circuitBreaker.before();

        //blabla

        if (suc) {
            circuitBreaker.recordSuccess();
        } else {
            circuitBreaker.recordFailure();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService scheduledExecutorService1 = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(getRunnable(), 0, 500, TimeUnit.MILLISECONDS);
        scheduledExecutorService1.scheduleAtFixedRate(getRunnable(), 0, 500, TimeUnit.MILLISECONDS);

        Thread.sleep(20 * 1000);

        suc = true;
        logger.info("switch to suc");

        Thread.sleep(10 * 1000);

    }

    private static Runnable getRunnable() {
        return () -> {
                    try {
                        work();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                };
    }
}
