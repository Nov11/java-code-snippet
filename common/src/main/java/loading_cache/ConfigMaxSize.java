package loading_cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class ConfigMaxSize {
    private static final Logger logger = LoggerFactory.getLogger(ConfigMaxSize.class);

    public static void main(String[] args) throws ExecutionException {
        RemovalListener<String, String> listener = new RemovalListener() {
            @Override
            public void onRemoval(RemovalNotification notification) {
                logger.info("notify : {}", notification);
            }
        };
        Cache<String, String> cache = CacheBuilder.newBuilder().removalListener(listener).maximumSize(1).build();
        String value1 = cache.get("k", new Callable() {
            @Override
            public Object call() throws Exception {
                return "v";
            }
        });

        String value2 = cache.get("kk", new Callable() {
            @Override
            public Object call() throws Exception {
                return "vv";
            }
        });
        Map<String, String> map = cache.getAllPresent(Collections.singleton("key"));

    }
}
