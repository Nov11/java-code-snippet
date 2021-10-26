package pkg.concat;

import com.google.common.collect.Sets;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.*;
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

    //    @Benchmark BHConcat.bh  avgt    6  333.858 ± 21.142  us/op
    public void bh(StateHolder stateHolder, Blackhole blackhole) {
        Set<List<String>> lists = Sets.cartesianProduct(stateHolder.s1, stateHolder.s2, stateHolder.s3);
        for (List<String> list : lists) {
            StringBuilder builder = new StringBuilder(40 * 3);
            for (String s : list) {
                builder.append(s);
                builder.append(":");
            }
            builder.deleteCharAt(builder.length() - 1);

            byte[] bytes = builder.toString().getBytes();
            blackhole.consume(bytes);
        }
    }

    //    @Benchmark
    public void bh2(StateHolder stateHolder, Blackhole blackhole) {
        Set<List<String>> lists = Sets.cartesianProduct(stateHolder.s1, stateHolder.s2, stateHolder.s3);
        for (List<String> list : lists) {
            byte[] bytes = String.join(":", list).getBytes();
            blackhole.consume(bytes);
        }
    }

    //    @Benchmark BHConcat.bh3  avgt    6  340.081 ± 20.667  us/op
    public void bh3(StateHolder stateHolder, Blackhole blackhole) {
        Set<List<String>> lists = Sets.cartesianProduct(stateHolder.s1, stateHolder.s2, stateHolder.s3);
        for (List<String> list : lists) {
            StringBuilder builder = new StringBuilder();
            for (String s : list) {
                builder.append(s);
                builder.append(":");
            }
            builder.deleteCharAt(builder.length() - 1);

            byte[] bytes = builder.toString().getBytes();
            blackhole.consume(bytes);
        }
    }

    //    @Benchmark BHConcat.bh4  avgt    6  316.266 ± 12.728  us/op
    public void bh4(StateHolder stateHolder, Blackhole blackhole) {
        Set<List<String>> lists = Sets.cartesianProduct(stateHolder.s1, stateHolder.s2, stateHolder.s3);
        for (List<String> list : lists) {
            StringBuilder builder = new StringBuilder(30 * 3);
            Iterator<String> iterator = list.iterator();
            builder.append(iterator.next());
            while (iterator.hasNext()) {
                builder.append(":");
                builder.append(iterator.next());
            }

            byte[] bytes = builder.toString().getBytes();
            blackhole.consume(bytes);
        }
    }

    //BHConcat.bh5  avgt    6  259.302 ± 1.523  us/op
//    @Benchmark
    public void bh5(StateHolder stateHolder, Blackhole blackhole) {
        Set<List<String>> lists = Sets.cartesianProduct(stateHolder.s1, stateHolder.s2, stateHolder.s3);
        char[] buff = new char[30 * 3];
        for (List<String> list : lists) {
            int idx = 0;
            Iterator<String> iterator = list.iterator();
            String first = iterator.next();
            first.getChars(0, first.length(), buff, idx);
            idx += first.length();

            while (iterator.hasNext()) {
                String cur = iterator.next();
                if (cur.length() + 1 > buff.length - idx) {
                    buff = Arrays.copyOf(buff, buff.length + 2 * (cur.length() + 1));
                }
                buff[idx++] = ':';
                cur.getChars(0, cur.length(), buff, idx);
                idx += cur.length();
            }

            byte[] bytes = new String(buff, 0, idx).getBytes();
            blackhole.consume(bytes);
        }
    }

    //0.610 us/op
//    @Benchmark
    public void createCartesianProduct(StateHolder stateHolder, Blackhole blackhole) {
        Set<List<String>> lists = Sets.cartesianProduct(stateHolder.s1, stateHolder.s2, stateHolder.s3);
        blackhole.consume(lists);
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
