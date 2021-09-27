package bytebufs;

import com.github.benmanes.caffeine.cache.*;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class CachedByteBuf {
    private static final Logger logger = getLogger(CachedByteBuf.class);

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        final Cache<Integer, ByteBuf> cache = Caffeine.newBuilder()
                .expireAfterWrite(1000, TimeUnit.SECONDS)
                .initialCapacity(10)
                .maximumSize(10)
                .removalListener(new RemovalListener<Integer, ByteBuf>() {
                    @Override
                    public void onRemoval(@Nullable Integer integer, @Nullable ByteBuf byteBuf, @NonNull RemovalCause removalCause) {
                        if (byteBuf == null) {
                            return;
                        }
                        byteBuf.release();
                        int ret = atomicInteger.decrementAndGet();
                        if (ret % 10 == 0) {
                            logger.info("atomic on removal : {}", ret);
                        }
                    }
                })
                .scheduler(Scheduler.forScheduledExecutorService(
                                Executors.newScheduledThreadPool(
                                        10,
                                        new ThreadFactoryBuilder().setNameFormat("name" + "-%d").setPriority(Thread.MAX_PRIORITY).build()
                                )
                        )
                )
//                .scheduler(Scheduler.forScheduledExecutorService(Executors.newScheduledThreadPool(3)))
                .build();

        new Thread(()->{
            while (!Thread.currentThread().isInterrupted()) {
                cache.cleanUp();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();;

        for (int i = 0; i < 1024; i++) {
            ByteBuf byteBuf = get();
            atomicInteger.incrementAndGet();
            cache.put(i, byteBuf);

            if (i % 10 == 0) {
                logger.info("atomic : {}", atomicInteger.get());
            }
        }
    }


    private static ByteBuf get() {
        return PooledByteBufAllocator.DEFAULT.directBuffer(5120);
    }
}
