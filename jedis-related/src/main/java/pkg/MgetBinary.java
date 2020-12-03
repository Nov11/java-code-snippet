package pkg;

import redis.clients.jedis.BinaryJedis;

import java.util.ArrayList;
import java.util.List;

public class MgetBinary {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MgetBinary.class);

    public static void main(String[] args) {
        BinaryJedis jedis = new BinaryJedis();
        byte[] b = new byte[0];
        jedis.set("foo".getBytes(), b);
        List<byte[]> ret = jedis.mget("foo".getBytes(), "bar".getBytes());
//        String s = new String(ret.get(0));
        logger.info("ret 0: {}", ret.get(0) == null);
        logger.info("ret 1: {}", ret.get(1) == null);
    }
}
