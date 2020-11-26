package pkg;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class Cache2k {

    static class W {
        byte[] b;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            W w = (W) o;
            return Arrays.equals(b, w.b);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(b);
        }
    }

    Cache<String, W> itemEmbeddingCache = Cache2kBuilder.of(String.class, W.class)
            .expireAfterWrite(Long.parseLong(System.getProperty("cache_ttl", "150")), TimeUnit.SECONDS) // 比 EMBEDDING_UPDATE_INTERVAL 短一些
            .entryCapacity(Long.parseLong(System.getProperty("cap", "1000000")))
            .boostConcurrency(true)
            .build();

    @Setup
    public void setup() {
        byte[] b = new byte[128 * 8];
        for (int i = 0; i < 128 * 8; i++) {
            b[i] = (byte) (i % 128);
        }
        for (int i = 0; i < 1000; i++) {
            String key = getKey(i);
            W w = new W();
            w.b = b;
            itemEmbeddingCache.put(key, w);
        }
    }

    private static String getKey(int i) {
        return "keykeykeykeykeykey_123456_" + i;
    }

    @Benchmark
    public void baseline(Blackhole blackhole) {
        List<W> result = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            result.add(itemEmbeddingCache.get(getKey(i)));
        }

        blackhole.consume(result);
    }


    @Benchmark
    public void baseline2(Blackhole blackhole) {
        List<W> result = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            result.add(new W());
        }

        blackhole.consume(result);
    }

    @Benchmark
    public void baseline3(Blackhole blackhole) {
        List<W> result = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            result.add(new W());
        }

        blackhole.consume(result);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Cache2k.class.getName() + ".*")
                // Set the following options as needed
                .mode(Mode.AverageTime)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .build();

        new Runner(opt).run();
    }
}
