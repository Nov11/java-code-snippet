package pkg;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class MgetTimeConsumption {
    private static final Logger logger = LoggerFactory.getLogger(MgetTimeConsumption.class);

    public static void main(String[] args) throws RunnerException, IOException {
        Options opt = new OptionsBuilder()
                .include(MgetTimeConsumption.class.getName())
                // Set the following options as needed
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .build();

        new Runner(opt).run();
    }

    @org.openjdk.jmh.annotations.State(Scope.Benchmark)
    public static class State {
        public String[] keys;
        public Jedis jedis;
        public List<String> list;
        public List<Response<String>> responses;

        @Setup
        public void setup() {
            keys = MakeKeys.generate(0).toArray(new String[]{});
            jedis = new Jedis();
            list = new ArrayList<>(keys.length);
            responses = new ArrayList<>(keys.length);
        }

        @TearDown
        public void teardown() {
            jedis.close();
        }
    }

    @Benchmark
    public void mget(Blackhole blackhole, State state) {
        Jedis jedis = state.jedis;
        String[] keys = state.keys;
        blackhole.consume(jedis.mget(keys));
    }

    @Benchmark
    public void get(Blackhole blackhole, State state) {
        Jedis jedis = state.jedis;
        String[] keys = state.keys;
        for (int i = 0; i < keys.length; i++) {
            blackhole.consume(jedis.get(keys[i]));
        }
    }

    @Benchmark
    public void getAndTransform(Blackhole blackhole, State state) {
        Jedis jedis = state.jedis;
        String[] keys = state.keys;
        List<String> list = new ArrayList<>(keys.length);
        for (int i = 0; i < keys.length; i++) {
            list.add(jedis.get(keys[i]));
        }
        blackhole.consume(list);
    }

    @Benchmark
    public void pipeline(Blackhole blackhole, State state) {
        Jedis jedis = state.jedis;
        String[] keys = state.keys;
        Pipeline pipeline = jedis.pipelined();
        for (String k : keys) {
            blackhole.consume(pipeline.get(k));
        }
        pipeline.sync();
    }

    @Benchmark
    public void pipelineAndTransform(Blackhole blackhole, State state) {
        Jedis jedis = state.jedis;
        String[] keys = state.keys;
        List<Response<String>> responses = new ArrayList<>(keys.length);
        Pipeline pipeline = jedis.pipelined();
        for (String k : keys) {
            responses.add(pipeline.get(k));
        }
        pipeline.sync();
        List<String> result = new ArrayList<>(keys.length);
        for (Response<String> response : responses) {
            result.add(response.get());
        }
        blackhole.consume(result);
    }
}
