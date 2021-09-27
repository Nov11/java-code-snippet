package pkg;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Fork(value = 2, warmups = 3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 4, time = 1)
@Measurement(iterations = 5, time = 1)
public class DotProduct {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(DotProduct.class.getName() + ".*")
                // Set the following options as needed
                .mode(Mode.AverageTime)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public void baseline(State state, Blackhole blackhole) {
        List<Double> result = new ArrayList<>();
        for (int j = 0; j < 10000; j++) {
            double tmp = 0;
            Param param = state.list.get(j);
            for (int i = 0; i < 128; i++) {
                tmp += param.a[i] * param.b[i];
            }
            result.add(tmp);
        }
        blackhole.consume(result);
    }

    static class Param {
        double[] a = new double[128];
        double[] b = new double[128];
    }

    @org.openjdk.jmh.annotations.State(Scope.Thread)
    public static class State {
        List<Param> list = new ArrayList<>();

        @Setup(Level.Iteration)
        public void setup() {
            for (int j = 0; j < 10000; j++) {
                Param param = new Param();
                for (int i = 0; i < 128; i++) {
                    param.a[i] = ThreadLocalRandom.current().nextDouble(1);
                    param.b[i] = ThreadLocalRandom.current().nextDouble(1);
                }
                list.add(param);
            }
        }
    }
}
