package pkg.concat;

import com.google.common.collect.Sets;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class BHConcat {
    public static void main(String[] args) throws RunnerException {
        BHConcat bhConcat = new BHConcat();
        bhConcat.doBH();
    }

    private void doBH() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BHConcat.class.getName())
                .jvmArgs("-Xmx2g",
                        "-Xms2g",
                        "-XX:+UnlockCommercialFeatures",
                        "-XX:+FlightRecorder",
                        "-XX:StartFlightRecording=settings=profile,filename=/tmp/test.jfr,dumponexit=true")
                .warmupIterations(2)
                .measurementIterations(2)
                .warmupTime(TimeValue.seconds(10))
                .measurementTime(TimeValue.seconds(10))
                .forks(3)
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MICROSECONDS)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public void bh(StateHolder stateHolder, Blackhole blackhole) {
        Set<List<String>> lists = Sets.cartesianProduct(stateHolder.s1, stateHolder.s2, stateHolder.s3);
        for (List<String> list : lists) {
            StringBuilder builder = new StringBuilder(30 * 3);
            for (String s : list) {
                builder.append(s);
                builder.append(":");
            }
            builder.deleteCharAt(builder.length() - 1);

            byte[] bytes = builder.toString().getBytes();
            blackhole.consume(bytes);
        }
    }

    @Benchmark
    public void bh2(StateHolder stateHolder, Blackhole blackhole) {
        Set<List<String>> lists = Sets.cartesianProduct(stateHolder.s1, stateHolder.s2, stateHolder.s3);
        for (List<String> list : lists) {
            byte[] bytes = String.join(":", list).getBytes();
            blackhole.consume(bytes);
        }
    }

    @State(Scope.Benchmark)
    public static class StateHolder {
        final Set<String> s1 = new HashSet<>();
        final Set<String> s2 = new HashSet<>();
        final Set<String> s3 = new HashSet<>();

        @Setup(Level.Trial)
        public void setup() {
            for (int i = 0; i < 10; i++) {
                s1.add(String.valueOf(ThreadLocalRandom.current().nextInt()));
            }
            for (int i = 0; i < 20; i++) {
                s2.add(String.valueOf(ThreadLocalRandom.current().nextInt()));
            }
            for (int i = 0; i < 10; i++) {
                s3.add(String.valueOf(ThreadLocalRandom.current().nextInt()));
            }
        }
    }
}
