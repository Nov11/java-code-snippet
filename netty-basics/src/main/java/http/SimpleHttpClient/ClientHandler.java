package http.SimpleHttpClient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpResponse fullHttpResponse = (FullHttpResponse) msg;
        logger.info("received: {}", fullHttpResponse);
        ByteBuf byteBuf = fullHttpResponse.content();
        String stringMessage = new String(ByteBufUtil.getBytes(byteBuf));
        logger.info("content : {}", stringMessage);
        ctx.close();
    }
}
