package pkg;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * *3
 * $3
 * SET
 * $3
 * foo
 * $3
 * bar
 * *3
 * $3
 * SET
 * $4
 * foo1
 * $4
 * bar1
 * +OK
 * +OK
 * *3
 * $3
 * SET
 * $5
 * N-foo
 * $5
 * N-bar
 * +OK
 */
public class PipelineTcpPackets {
    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = new Jedis();
        Pipeline pipeline = jedis.pipelined();
        pipeline.set("foo", "bar");
        pipeline.set("foo1", "bar1");
        pipeline.sync();

        jedis.set("N-foo", "N-bar");

        jedis.close();

        Thread.sleep(2000);
    }
}
