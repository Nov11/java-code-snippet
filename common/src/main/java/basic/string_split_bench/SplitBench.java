package basic.string_split_bench;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.RunnerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.jvm.hotspot.utilities.Assert;

import java.io.IOException;

public class SplitBench {
    private static final Logger logger = LoggerFactory.getLogger(SplitBench.class);
    private static final String s = "1234567890_abcdefghijklmn_opqrstuvwzyz";

    public static void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void init() {
        oneRun();
    }

    private static void oneRun() {
        String[] ret = s.split("_");
        Assert.that(ret.length == 3, "msg");
    }
}
