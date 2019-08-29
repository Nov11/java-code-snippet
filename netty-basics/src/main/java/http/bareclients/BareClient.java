package http.bareclients;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * no connection pooling
 * one request means one freshly allocated connection, and the connection is closed after response is received
 * <p>
 * naive implementation but let me finish this first
 */
public class BareClient {
    private static final Logger logger = LoggerFactory.getLogger(BareClient.class);
    private static final int HTTP_MAX_LENGTH_LIMIT = 2 * 1024 * 1024;//2MB
    private EventExecutorGroup eventExecutors;
    private EventLoopGroup eventLoopGroup;

    private int connectionTimeoutMills;
    private int socketTimeoutMills;

    public BareClient(EventExecutorGroup eventExecutors,
                      EventLoopGroup eventLoopGroup,
                      int connectionTimeout,
                      int socketTimeout) {
        this.eventExecutors = eventExecutors;
        this.eventLoopGroup = eventLoopGroup;
        this.connectionTimeoutMills = connectionTimeout;
        this.socketTimeoutMills = socketTimeout;
    }

    public BareClient() {
        this(new DefaultEventExecutorGroup(1),
                new NioEventLoopGroup(),
                100,
                100);
    }

    public ChannelFuture connect(String host, int port) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)
                .group(eventLoopGroup)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpClientCodec());
                        pipeline.addLast(new HttpObjectAggregator(HTTP_MAX_LENGTH_LIMIT));
                        pipeline.addLast(eventExecutors, new NaiveHandler());
                    }
                })
                .option(ChannelOption.AUTO_READ, false)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeoutMills)
                .option(ChannelOption.SO_TIMEOUT, socketTimeoutMills)
                .option(ChannelOption.TCP_NODELAY, true);

        return bootstrap.connect(host, port);
    }

    public ChannelFuture request(FullHttpRequest fullHttpRequest, ChannelFuture connected) {
        return connected.channel().writeAndFlush(fullHttpRequest);
    }

    public void processResponse(ChannelFuture response) {
    }
}
