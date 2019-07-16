package pkg.util;

import io.github.nov11.TimGroupStatsDClient;
import io.github.nov11.udp.UdpClient;

public class StatsdClient {
    public static final com.timgroup.statsd.StatsDClient STATS_D_CLIENT = new TimGroupStatsDClient("prefix1234.qtp", new UdpClient("localhost", 8125));
}
