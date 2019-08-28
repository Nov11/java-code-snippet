package baeldung.netty.server;

import baeldung.netty.common.Request;
import baeldung.netty.common.Response;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Handler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(Handler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("channel read");
        Request request = (Request) msg;
        Response response = new Response();
        response.setReturnValue(request.getIntValue() * 2);
        ChannelFuture future = ctx.writeAndFlush(response);
        future.addListener(ChannelFutureListener.CLOSE);
        logger.info("Handler executed");
    }
}
