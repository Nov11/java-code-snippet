package loading_cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

//refresh is called on requesting stale value
public class LoadingCacheRefresh {
    private static final Logger logger = LoggerFactory.getLogger(LoadingCacheRefresh.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
                .refreshAfterWrite(100, TimeUnit.MILLISECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        logger.info("load called");
                        return "load";
                    }

                    @Override
                    public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
                        logger.info("reload called");
                        return super.reload(key, oldValue);
                    }
                });

        String value = loadingCache.get("A");
        logger.info("value :{}", value);

        Thread.sleep(1000);

        value = loadingCache.get("B");

        logger.info("value :{}", value);
    }
}
