package http.SimpleHttpClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

public class Client {
    private int port;
    private String host;
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private static FullHttpRequest fullHttpRequest() {
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/ss");
        request.headers().add("host", "localhost:8090");
        return request;
    }

    public void start() throws InterruptedException {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class)
                    .group(eventLoopGroup)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new HttpClientCodec())
                                    .addLast(new HttpObjectAggregator(512 * 1024))
                                    .addLast(new ClientHandler());
                        }
                    })
                    .option(ChannelOption.TCP_NODELAY, true);

            ChannelFuture future = bootstrap.connect(host, port).sync();
            Channel channel = future.channel();
            channel.writeAndFlush(fullHttpRequest());
            channel.closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client("localhost", 8090);
        client.start();
    }
}
