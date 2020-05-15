package pkg.set;

import redis.clients.jedis.Jedis;

import java.time.Duration;
import java.time.LocalDateTime;

public class HugeSet {
    public static void main(String[] args) {
        Jedis jedis = new Jedis();
//        for (int i = 0; i < 4_0000_0000; i++) {
//            jedis.sadd("bigset", i + "");
//        }
//        System.out.println("done");

        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < 200; i++) {
            System.out.println(jedis.sismember("bigset", i + ""));
        }
        LocalDateTime end = LocalDateTime.now();
        System.out.println(Duration.between(now, end).toMillis());
    }
}
