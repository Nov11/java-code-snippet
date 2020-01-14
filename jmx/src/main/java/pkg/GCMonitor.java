package pkg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class GCMonitor {
    private static final Logger logger = LoggerFactory.getLogger(GCMonitor.class);

    public static void main(String[] args) throws InterruptedException {
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        garbageCollectorMXBeans.forEach(b -> {
            logger.info("name : {}", b.getName());
            String[] pools = b.getMemoryPoolNames();
            for (String s : pools) {
                logger.info("{}", s);
            }
        });

        new CountDownLatch(1).await();
    }
}
