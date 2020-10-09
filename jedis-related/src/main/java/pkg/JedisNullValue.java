package pkg;

import redis.clients.jedis.Jedis;

public class JedisNullValue {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis://localhost:6379");
        jedis.set("k".getBytes(), new byte[0]);
        byte[] bytes = jedis.get("k".getBytes());
        System.out.println(bytes.length);
    }
}
