package pkg;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Fork(2)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
public class JMHAnnotations {
    private static final Logger logger = LoggerFactory.getLogger(JMHAnnotations.class);

    @Benchmark
    public int sum(DumbState state, Blackhole blackhole) {
        blackhole.consume(state.value);
        return 1;
    }

    @Benchmark
    public int sum2(DumbState state, Blackhole blackhole) {
        blackhole.consume(state.value);
        return 1;
    }

    @State(Scope.Benchmark)
    public static class DumbState {
        public String value;

        @Setup(Level.Trial)
        public void setup() {
            logger.info("setup called");
            System.err.println("setup called");
            value = System.currentTimeMillis() + "";
        }

        public DumbState() {
            System.err.println("ctor called");
        }
    }

    public static void main(String[] args) throws IOException, RunnerException {
        logger.info("main");
        Options opt = new OptionsBuilder()
                .include(JMHAnnotations.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }
}
