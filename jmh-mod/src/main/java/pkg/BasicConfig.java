package pkg;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BasicConfig {
    @Benchmark
    public void init() {
        // Do nothing
    }

    public static void main(String[] args) throws Exception {
        //better config opt then call run
        //org.openjdk.jmh.Main.main(args);
        Options opt = new OptionsBuilder()
                .include(BasicConfig.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }
}
