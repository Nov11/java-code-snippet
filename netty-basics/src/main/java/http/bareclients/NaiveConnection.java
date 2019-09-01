package http.bareclients;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

/**
 * netty's working model contains all the traffic processing inside the pipeline.
 * this implies passing out response from pipeline must be done use a wrapper around handlers.
 * results are updated inside pipeline and the future that will hold the result is passed out.
 * <p>
 * one client object manages connections
 * one connection manages read & write messages and operates with the handler
 */
public class NaiveConnection {
    private static final Logger logger = LoggerFactory.getLogger(BareClient.class);
    private static final int HTTP_MAX_LENGTH_LIMIT = 2 * 1024 * 1024;//2MB
    //just hold references but owning theses resources
    private EventExecutorGroup eventExecutors;
    private EventLoopGroup eventLoopGroup;

    private int connectionTimeoutMills;
    private int socketTimeoutMills;
    private CompletableFuture<byte[]> result = new CompletableFuture<>();

    private ChannelFuture connectChannelFuture;

    public NaiveConnection(EventExecutorGroup eventExecutors,
                           EventLoopGroup eventLoopGroup,
                           int connectionTimeout,
                           int socketTimeout) {
        this.eventExecutors = eventExecutors;
        this.eventLoopGroup = eventLoopGroup;
        this.connectionTimeoutMills = connectionTimeout;
        this.socketTimeoutMills = socketTimeout;
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
                        pipeline.addLast(eventExecutors, new NaiveHandler(result));
                    }
                })
                .option(ChannelOption.AUTO_READ, false)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeoutMills)
                //Unknown channel option 'SO_TIMEOUT' for channel '[id: 0xf711ca52]'
//                .option(ChannelOption.SO_TIMEOUT, socketTimeoutMills)
                .option(ChannelOption.TCP_NODELAY, true);

        connectChannelFuture = bootstrap.connect(host, port);
        return connectChannelFuture;
    }

    public CompletableFuture<byte[]> request(FullHttpRequest fullHttpRequest) {
        connectChannelFuture.channel().writeAndFlush(fullHttpRequest);
        return result;
    }
}
