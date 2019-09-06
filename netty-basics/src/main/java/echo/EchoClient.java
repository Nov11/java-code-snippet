package echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class EchoClient {
    private static final Logger logger = LoggerFactory.getLogger(EchoClient.class);
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);

    private static class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
//            ctx.writeAndFlush(Unpooled.copiedBuffer("client message", CharsetUtil.UTF_8));
//            logger.info("channel active msg sent");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            logger.info("inactive");
        }

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
            logger.info("received from server: {}", byteBuf.toString(CharsetUtil.UTF_8));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            logger.error("ex:", cause);
            ctx.close();
        }
    }

    public void start() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("readtimeout", new MongoDriverReadTimeOutHandler(10));
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    })
                    .remoteAddress(new InetSocketAddress("localhost", 8090));
            ChannelFuture future = bootstrap.connect().sync();

            String msg = "msg";
            future.channel().writeAndFlush(Unpooled.wrappedBuffer(msg.getBytes()));

            future.channel().closeFuture().sync();
            logger.info("close future returned");
            Thread.sleep(1000);
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        EchoClient echoClient = new EchoClient();
        echoClient.start();
    }
}
