package loading_cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.SettableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CachingFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
    }

    private static void settableFuture() throws ExecutionException, InterruptedException {
        LoadingCache<String, SettableFuture<String>> loadingCache = CacheBuilder.newBuilder()
                .build(new CacheLoader<String, SettableFuture<String>>() {
                    @Override
                    public SettableFuture<String> load(String key) throws Exception {
                        SettableFuture<String> future = SettableFuture.create();
                        future.setException(new RuntimeException("ex"));
                        return future;
                    }
                });

        SettableFuture<String> v = loadingCache.get("k");
        System.out.println(v.get());
    }

    private static void cacheCompletableFuture() throws ExecutionException, InterruptedException {
        LoadingCache<String, CompletableFuture<String>> loadingCache = CacheBuilder.newBuilder()
                .build(new CacheLoader<String, CompletableFuture<String>>() {
                    @Override
                    public CompletableFuture<String> load(String key) throws Exception {
                        CompletableFuture<String> future = new CompletableFuture<>();
                        future.completeExceptionally(new RuntimeException("ex"));
                        return future;
                    }
                });

        CompletableFuture<String> v = loadingCache.get("k");
        System.out.println(v.get());
    }
}
