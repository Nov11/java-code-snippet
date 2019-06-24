package ppkkgg;

import com.timgroup.statsd.StatsDClient;
import net.jodah.failsafe.CircuitBreaker;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReproducingNotRecoveringScenario {
    private static final StatsDClient statsDClient = TimStatsDClient.getClient();
    private static CircuitBreaker circuitBreaker = new CircuitBreaker()
            .withDelay(1, TimeUnit.SECONDS)
            .withFailureThreshold(3, 5)
            .withSuccessThreshold(1, 1);
    private static final Method before = MethodUtils.getMatchingMethod(CircuitBreaker.class, "before");
    private static final Method recordFailure = MethodUtils.getMatchingMethod(CircuitBreaker.class, "recordFailure");
    private static final Method recordSuccess = MethodUtils.getMatchingMethod(CircuitBreaker.class, "recordSuccess");
    private static boolean returnValue = false;

    private static boolean allow() {
        if (circuitBreaker.allowsExecution()) {
            statsDClient.increment("suc");
            return true;
        }
        statsDClient.increment("fail");
        return false;
    }

    private static void work() throws InvocationTargetException, IllegalAccessException {
        if (!allow()) {
            return;
        }

        before.invoke(circuitBreaker);

        //blabla

        if (returnValue) {
            recordFailure.invoke(circuitBreaker, new Exception("failure"));
        } else {
            recordSuccess.invoke(circuitBreaker);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {
                    try {
                        work();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }, 0, 1, TimeUnit.SECONDS
        );

        Thread.sleep(20 * 1000);
        returnValue = true;

        Thread.sleep(10 * 1000);
    }
}
