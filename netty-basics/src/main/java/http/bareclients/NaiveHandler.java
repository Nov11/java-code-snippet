package http.bareclients;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class NaiveHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(NaiveHandler.class);
    private CompletableFuture<byte[]> responseContent;

    public NaiveHandler(CompletableFuture<byte[]> responseContent) {
        this.responseContent = responseContent;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("naive handler called");
        if (!(msg instanceof FullHttpResponse)) {
            throw new IllegalArgumentException("msg is not of type full http response");
        }

        FullHttpResponse fullHttpResponse = (FullHttpResponse) msg;

        ByteBuf byteBuf = fullHttpResponse.content();

        byte[] bytes = ByteBufUtil.getBytes(byteBuf);
        responseContent.complete(bytes);

        logger.info("read: {}", bytes);

        ReferenceCountUtil.release(msg);
        ctx.close();
    }
}
