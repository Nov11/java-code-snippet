package loading_cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CacheNull {
    public static void main(String[] args) {
        Cache<String, String> cache = CacheBuilder.newBuilder().build();
        cache.put("k", null);
    }
}
