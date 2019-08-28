package baeldung.netty.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseEncoder extends MessageToByteEncoder<Response> {
    private static final Logger logger = LoggerFactory.getLogger(ResponseEncoder.class);
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Response response, ByteBuf byteBuf) throws Exception {
        logger.info("encode");
        byteBuf.writeInt(response.getReturnValue());
    }
}
