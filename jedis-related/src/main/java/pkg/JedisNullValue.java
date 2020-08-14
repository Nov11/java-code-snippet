package pkg;

import redis.clients.jedis.Jedis;

public class JedisNullValue {
    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        jedis.set("k".getBytes(), new byte[0]);
    }
}
