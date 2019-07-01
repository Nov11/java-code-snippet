package pkg;

import redis.clients.jedis.BinaryJedis;

import java.util.List;

public class MgetBinary {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MgetBinary.class);

    public static void main(String[] args) {
        BinaryJedis jedis = new BinaryJedis();
        List<byte[]> ret = jedis.mget("foo".getBytes());
        String s = new String(ret.get(0));
        logger.info("ret : {}", s);
    }
}
