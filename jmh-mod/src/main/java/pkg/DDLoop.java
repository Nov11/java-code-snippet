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
 * Benchmark            Mode  Cnt    Score    Error  Units
 * DDLoop.em            avgt    5    0.267 ±  0.006  ns/op
 * DDLoop.measureRight  avgt    5    2.407 ±  0.165  ns/op
 * DDLoop.measure_1     avgt    5    2.391 ±  0.012  ns/op
 * DDLoop.measure_10    avgt    5   23.898 ±  2.237  ns/op
 * DDLoop.measure_100   avgt    5  248.028 ± 18.296  ns/op
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class DDLoop {
    int x = 1;
    int y = 2;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(DDLoop.class.getName() + ".*")
                // Set the following options as needed
                .mode(Mode.AverageTime)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .build();

        new Runner(opt).run();
    }

    private int workImpl() {
        return x + y;
    }

    private int work() {
        int result = 0;
        for (int i = 0; i < 100; i++) {
            result += workImpl();
        }
        return result;
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
