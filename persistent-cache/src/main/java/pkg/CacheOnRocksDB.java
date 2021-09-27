package pkg;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.checkerframework.checker.units.qual.C;
import org.rocksdb.RocksDB;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class CacheOnRocksDB {
    private static final Logger logger = getLogger(CacheOnRocksDB.class);

    static {
        RocksDB.loadLibrary();
    }

    private final Cache<byte[], byte[]> cache;

    public CacheOnRocksDB() {
        cache = CacheBuilder.newBuilder()
                .initialCapacity(10)
                .maximumSize(20)
                .removalListener(new Flusher())
                .build();
    }

    private static class Flusher implements RemovalListener<byte[], byte[]> {
        @Override
        public void onRemoval(RemovalNotification<byte[], byte[]> removalNotification) {
            logger.info("notification : {}", removalNotification.getCause());
        }
    }

    public void put(byte[] k, byte[] v) {
        cache.put(k, v);
    }

    public Cache<byte[], byte[]> getCache() {
        return cache;
    }

    public byte[] get(byte[] k) {
        return null;
    }

    public static void main(String[] args) {
        CacheOnRocksDB cache = new CacheOnRocksDB();
        Cache<byte[], byte[]> cache1 = cache.getCache();
        cache1.put(new byte[1], new byte[1]);
    }
}
