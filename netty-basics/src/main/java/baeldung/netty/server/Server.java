package baeldung.netty.server;

import baeldung.netty.common.RequestDecoder;
import baeldung.netty.common.ResponseEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

public class Server {

    public void start() {
        EventExecutorGroup eventExecutors = new DefaultEventExecutorGroup(2);
        EventExecutorGroup eventExecutors2 = new DefaultEventExecutorGroup(2);
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(eventExecutors, new RequestDecoder())
                                    .addLast(new ResponseEncoder())
                                    .addLast(eventExecutors2, new Handler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.bind(8888).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
