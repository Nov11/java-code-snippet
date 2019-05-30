package ppkkgg;

import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.SyncFailsafe;
import net.jodah.failsafe.function.CheckedConsumer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Test {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Test.class);

    static class failure implements CheckedConsumer<Throwable> {
        @Override
        public void accept(Throwable throwable) throws Exception {
            logger.info("tracing.failure");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String def = "default value";
        CircuitBreaker circuitBreaker = new CircuitBreaker()
                .withDelay(50, TimeUnit.MILLISECONDS)
                .withFailureThreshold(1, 2)
                .withSuccessThreshold(2, 5);
        SyncFailsafe<String> failsafe = Failsafe.<String>with(circuitBreaker)

                .onAbort((r) -> logger.info("tracing.circuitOpen"))
                .onFailure(new failure());
//                .withFallback(def);

        runTime(circuitBreaker, failsafe);

        Thread.sleep(50);

        logger.info("@@@@@@after 50 ms");
        runTime(circuitBreaker, failsafe);
    }

    private static void runTime(CircuitBreaker circuitBreaker, SyncFailsafe<String> failsafe) {
        logger.info("in the beginning : {} failure threshold : {} succ : {}", circuitBreaker.getState(), circuitBreaker.getFailureThreshold().ratio, circuitBreaker.getSuccessThreshold().ratio);
        for (int i = 0; i < 10; i++) {
            logger.info("=====");
            try {
                String ret = failsafe.get(() -> {
                    work();
                    return "expect value";
                });
                logger.info("i : {} result : {} state : {}", i, ret, circuitBreaker.getState());
            }catch (Exception e){}
        }
    }

    static int cnt = 0;

    private static void work() throws IOException {
        cnt++;
        logger.info("cnt : {}", cnt);
        if (cnt > 2) {
            return;
        }
        throw new IOException("lalalal");
    }
}
