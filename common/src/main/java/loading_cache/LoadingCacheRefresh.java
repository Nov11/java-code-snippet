package loading_cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

//refresh is called on requesting stale value
public class LoadingCacheRefresh {
    private static final Logger logger = LoggerFactory.getLogger(LoadingCacheRefresh.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
                .refreshAfterWrite(500, TimeUnit.MILLISECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        logger.info("load called");
                        return "load";
                    }

                    @Override
                    public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
                        logger.info("reload called");
                        SettableFuture<String> ret = SettableFuture.create();
                        CompletableFuture<Void> c = CompletableFuture.supplyAsync(() -> {
                            logger.info("calling");
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ret.set("result_set");
                            return null;
                        });
                        return ret;
                    }
                });

        String value = loadingCache.get("A");
        logger.info("ret value :{}", value);

        Thread.sleep(1000);

        value = loadingCache.get("A");

        logger.info("ret value :{}", value);

        Thread.sleep(200);

        value = loadingCache.get("A");

        logger.info("ret value :{}", value);

    }
}
