package oldstyleserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

public class DumbClient {
    private int port;
    private String host;
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);

    public DumbClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        DumbClient client = new DumbClient("localhost", 8080);
        client.start();
    }

    public void start() throws InterruptedException {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class)
                    .group(eventLoopGroup)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new SimpleChannelInboundHandler<ByteBuf>() {

                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                            String s = msg.toString(CharsetUtil.UTF_8);
                                            System.out.println("get:" + s);
                                        }
                                    });
                        }
                    })
                    .option(ChannelOption.TCP_NODELAY, true);

            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
