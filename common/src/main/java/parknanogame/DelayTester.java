package parknanogame;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.RunnerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.locks.LockSupport;

/**
 * park 10 us ～ 20us blocking
 *      55 us ～ 80us blocking
 *       5 us ~  12us blocking
 *      15 us ~  26us blocking
 */
public class DelayTester {
    private static final Logger logger = LoggerFactory.getLogger(DelayTester.class);

    public static void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void init() {
        oneRun(10000);
    }

    private static void oneRun(int iteration) {
        long startNanos = System.nanoTime();

        for (long i = 0; i < iteration; i++) {
            LockSupport.parkNanos(15000);
        }

        long durationNanos = System.nanoTime() - startNanos;

        long durationMills = Duration.ofNanos(durationNanos).toMillis();
        long microPerIteration = durationNanos / 1000 / iteration;

        logger.info("iteration {} - {} ms | us per iteration : {} us", iteration, durationMills, microPerIteration);
    }
}
