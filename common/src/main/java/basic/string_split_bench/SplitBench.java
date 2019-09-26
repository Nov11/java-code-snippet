package basic.string_split_bench;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;


//@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
//@State(Scope.Benchmark)
//@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
public class SplitBench {
    private static final Logger logger = LoggerFactory.getLogger(SplitBench.class);
    //    private static final String s = "1234567890_abcdefghijklmn_opqrstuvwzyz";
    private static List<String> list;

    private static void load() {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("/Users/c6s/tosplit"));
            ObjectMapper objectMapper = new ObjectMapper();
            list = objectMapper.readValue(fileInputStream, new TypeReference<List<String>>() {
            });
            return;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        list = Collections.emptyList();
    }

    static {
        load();
    }

    public static void main(String[] args) throws IOException, RunnerException {
//        String test = "1234567890_abcdefghijklmn_opqrstuvwzyz";
//        for (String s : tokernizer(test)) {
//            logger.info("{}", s);
//        }
        testDrive();
    }

    private static void testDrive() throws RunnerException {
        load();
        Options opt = new OptionsBuilder()
                .include(SplitBench.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void init() {
        oneRun(SplitBench::tokernizer);
    }

    private static void oneRun(Function<String, String[]> function) {
        for (int i = 0; i < 2; i++) {
            for (String item : list) {
//                String[] s = item.split("_");
                String[] s = function.apply(item);
                Assert.isTrue(s.length == 3, "blabla");
            }
        }
    }

    //hand made
    private static String[] sss(String s) {
        List<String> result = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '_') {
                result.add(builder.toString());
                builder.setLength(0);
            } else {
                builder.append(c);
            }
        }

        if (builder.length() > 0) {
            result.add(builder.toString());
        }
        return result.toArray(new String[0]);
    }

    //tokenizer
    private static String[] tokernizer(String s) {
        StringTokenizer stringTokenizer = new StringTokenizer(s, "_");
        List<String> result = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            result.add(stringTokenizer.nextToken());
        }

        return result.toArray(new String[0]);
    }
}
