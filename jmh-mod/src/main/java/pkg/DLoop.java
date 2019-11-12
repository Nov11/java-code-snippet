package pkg;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * # Run complete. Total time: 00:01:23
 * <p>
 * Benchmark           Mode  Cnt    Score    Error  Units
 * DLoop.em            avgt    5    0.256 ±  0.002  ns/op
 * DLoop.measureRight  avgt    5    1.971 ±  0.152  ns/op
 * DLoop.measure_1     avgt    5    1.976 ±  0.148  ns/op
 * DLoop.measure_10    avgt    5   18.340 ±  1.570  ns/op
 * DLoop.measure_100   avgt    5  191.871 ± 12.999  ns/op
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class DLoop {
    int x = 1;
    int y = 2;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(DLoop.class.getName() + ".*")
                // Set the following options as needed
                .mode(Mode.AverageTime)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .build();

        new Runner(opt).run();
    }

    private int work() {
        return x + y;
    }

    @Benchmark
    public int measureRight() {
        return work();
    }

    //    @Benchmark
    private void emImpl1(int reps, Blackhole bh) {
        for (int i = 0; i < reps; i++) {
            bh.consume(work());
        }
    }

    @Benchmark
    @OperationsPerInvocation(1)
    public void measure_1(Blackhole bh) {
        emImpl1(1, bh);
    }

    @Benchmark
    @OperationsPerInvocation(1)
    public void measure_10(Blackhole bh) {
        emImpl1(10, bh);
    }

    @Benchmark
    @OperationsPerInvocation(1)
    public void measure_100(Blackhole bh) {
        emImpl1(100, bh);
    }

    @Benchmark
    public void em() {

    }
}
