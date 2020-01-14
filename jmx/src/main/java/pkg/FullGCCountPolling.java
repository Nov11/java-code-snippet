package pkg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FullGCCountPolling {
    private static final Logger logger = LoggerFactory.getLogger(FullGCCountPolling.class);
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public static void monitorFullGC() {
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        GarbageCollectorMXBean old = null;
        for (GarbageCollectorMXBean bean : garbageCollectorMXBeans) {
            if (bean.getName().contains("Old")) {
                old = bean;
                break;
            }
        }
        if (old == null) {
            return;
        }
        GarbageCollectorMXBean target = old;
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                logger.info("full gc count : {}", target.getCollectionCount());
                                                            }
                                                        },
                0, 1, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {

    }
}
