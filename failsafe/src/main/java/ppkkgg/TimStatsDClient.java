package ppkkgg;

import io.github.nov11.TimGroupStatsDClient;
import io.github.nov11.udp.UdpClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 10 times per second
 * 10ms mean_90
 */
public class TimStatsDClient {
    private static final com.timgroup.statsd.StatsDClient STATS_D_CLIENT = new TimGroupStatsDClient("local", new UdpClient("localhost", 8125));

    public static com.timgroup.statsd.StatsDClient getClient() {
        return STATS_D_CLIENT;
    }

    private static void work() {
        STATS_D_CLIENT.time("a", 10);
    }

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(TimStatsDClient::work, 0, 100, TimeUnit.MILLISECONDS);
    }
}
