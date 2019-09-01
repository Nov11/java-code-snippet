package http.bareclients;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * no connection pooling
 * one request means one freshly allocated connection, and the connection is closed after response is received
 * <p>
 * naive implementation but let me finish this first
 * <p>
 * todo:
 * builder pattern
 */
public class BareClient {
    private EventExecutorGroup eventExecutors;
    private EventLoopGroup eventLoopGroup;
    private int connectionTimeoutMills;
    private int socketTimeoutMills;

    public BareClient() {
        eventExecutors = new DefaultEventExecutorGroup(1);
        eventLoopGroup = new NioEventLoopGroup();

        connectionTimeoutMills = 100;
        socketTimeoutMills = 100;
    }

    public NaiveConnection makeConnection() {
        return new NaiveConnection(eventExecutors, eventLoopGroup, connectionTimeoutMills, socketTimeoutMills);
    }
}
