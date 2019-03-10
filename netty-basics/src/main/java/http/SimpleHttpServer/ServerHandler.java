package http.SimpleHttpServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class ServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
        String content = fullHttpRequest.content().toString();
        logger.info("get from client : {}", content);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.content().writeCharSequence("server received: " + content, StandardCharsets.UTF_8);
        ctx.writeAndFlush(response).channel().close();
    }
}
