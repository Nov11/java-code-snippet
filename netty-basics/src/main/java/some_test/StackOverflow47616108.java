package some_test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StackOverflow47616108 {
    private static final Logger logger = LoggerFactory.getLogger(StackOverflow47616108.class);

    public static class ServerInitializer extends ChannelInitializer<SocketChannel> {
        String TAG = "LOG: ";

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            System.out.println(TAG + "Starting ServerInitializer class...");
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
            pipeline.addLast("encoder", new ObjectEncoder());
            pipeline.addLast("idleStateHandler", new IdleStateHandler(6, 3, 0, TimeUnit.SECONDS));
            pipeline.addLast("handler", new ServerHandler());
        }
    }

    public static class ServerHandler extends ChannelInboundHandlerAdapter {

        private String TAG = "LOG: ";

        public ServerHandler() {
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            logger.info("New Client become connected, Sending a message to the Client. Client Socket is: " + ctx.channel().remoteAddress().toString());

            List<String> msg = new ArrayList<>();
            msg.add(0, "sample message 1");
            msg.add(1, "sample message 2");
            sendMessage(ctx, msg);
        }

        public void sendMessage(ChannelHandlerContext ctx, List message) {
            ctx.write(message);
            ctx.flush();
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
            logger.info("A Client become disconnected. Client Socket is: " + ctx.channel().remoteAddress().toString() + " id: " + (ctx.channel().hashCode()));
            //COnnection id dead, do something here...
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object object) { // (2)
            logger.info("CLIENT: " + ctx.channel().remoteAddress().toString() + " SAYS: " + object);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
            // Close the connection for that client  when an exception is raised.
            logger.info("Something's wrong, CLIENT: " + ctx.channel().remoteAddress().toString() + " CAUSE: " + cause.toString());
            ctx.close();
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            logger.info("userEventTriggered");
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent e = (IdleStateEvent) evt;
                logger.info("evt : {}", e);
                if (e.state() == IdleState.READER_IDLE) {
                    ctx.close(); //Closed the Channel so that the `channelInactive` will be trigger
                } else if (e.state() == IdleState.WRITER_IDLE) {
                    ctx.writeAndFlush("ping\n"); //Send ping to client
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer());
        ChannelFuture channelFuture = serverBootstrap.bind(8090).sync();
        channelFuture.channel().closeFuture().sync();
    }
}
