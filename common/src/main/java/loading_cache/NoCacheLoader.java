package loading_cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NoCacheLoader {
    private static final Logger logger = LoggerFactory.getLogger(NoCacheLoader.class);

    public static void main(String[] args) throws InterruptedException {
        Cache<String, String> cache = CacheBuilder.newBuilder()
//                .refreshAfterWrite(100, TimeUnit.MILLISECONDS)
                .expireAfterWrite(100, TimeUnit.MILLISECONDS)
                .build();

        cache.put("k", "v");
        Map<String, String> map = cache.getAllPresent(Collections.singletonList("abc"));
        logger.info("map size : {}", map.size());

        Thread.sleep(200);

        logger.info("{} : {}", "k", cache.getIfPresent("k"));
    }
}
