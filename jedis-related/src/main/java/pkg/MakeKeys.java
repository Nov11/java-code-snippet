package pkg;

import javafx.util.Pair;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 50, 500, 5000 as key count
 * 1000 as value length
 * generator key value pairs according to a config
 */
public class MakeKeys {
    private static int[] KEY_NUMBER = new int[]{50, 500, 5000};
    private static int[] VALUE_LENGTH = new int[]{1000, 1000, 1000};

    public static void main(String[] args) {
        generate(0);
    }

    public static List<String> generate(int configIdx) {
        Jedis jedis = new Jedis();

        List<Pair<String, byte[]>> result = generateKV(KEY_NUMBER[configIdx], VALUE_LENGTH[configIdx]);

        for (Pair<String, byte[]> pair : result) {
            jedis.set(pair.getKey().getBytes(), pair.getValue());
        }

        jedis.close();

        return result.stream().map(Pair::getKey).collect(Collectors.toList());
    }

    static List<Pair<String, byte[]>> generateKV(int keyCount, int valueLength) {
        byte[] value = new byte[valueLength];
        Random random = new Random();
        List<Pair<String, byte[]>> result = new ArrayList<>();
        for (int i = 0; i < keyCount; i++) {
            random.nextBytes(value);
            result.add(new Pair<String, byte[]>("K:" + i, value));
        }
        return result;
    }
}
