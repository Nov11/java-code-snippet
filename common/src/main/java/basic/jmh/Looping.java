package basic.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class Looping {
    int x = 1;
    int y = 2;

    @Benchmark
    public int measureRight() {
        return (x + y);
    }

    private int reps(int reps) {
        int s = 0;
        for (int i = 0; i < reps; i++) {
            s += (x + y);
        }
        return s;
    }

    private void emImpl1(int reps, Blackhole bh) {
        for (int i = 0; i < reps; i++) {
             bh.consume(x + y);
        }
    }

    private void emImpl2(int reps, Blackhole bh) {
        for (int i = 0; i < reps; i++) {
            bh.consume(x + y);
        }
    }

    @Benchmark
    @OperationsPerInvocation(1)
    public int measureWrong_1() {
        return reps(1);
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public int measureWrong_10() {
        return reps(10);
    }

    @Benchmark
    @OperationsPerInvocation(100)
    public int measureWrong_100() {
        return reps(100);
    }

    @Benchmark
    @OperationsPerInvocation(1000)
    public int measureWrong_1000() {
        return reps(1000);
    }

    @Benchmark
    @OperationsPerInvocation(10000)
    public int measureWrong_10000() {
        return reps(10000);
    }

    @Benchmark
    @OperationsPerInvocation(100000)
    public int measureWrong_100000() {
        return reps(100000);
    }

    @Benchmark
    public void em() {

    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Looping.class.getName() + ".*")
                // Set the following options as needed
                .mode(Mode.AverageTime)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .build();

        new Runner(opt).run();
    }
}
