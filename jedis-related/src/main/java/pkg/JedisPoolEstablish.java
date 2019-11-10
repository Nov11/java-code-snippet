package pkg;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolEstablish {
    public static void main(String[] args) {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool(poolConfig, "redis://localhost:6379");
        //no connection is made during jedis pool construction
        System.out.println("done");
    }
}
