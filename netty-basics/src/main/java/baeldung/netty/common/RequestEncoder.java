package baeldung.netty.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

public class RequestEncoder extends MessageToByteEncoder<Request> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Request request, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(request.getIntValue());
        byteBuf.writeInt(request.getStringValue().length());
        byteBuf.writeCharSequence(request.getStringValue(), StandardCharsets.UTF_8);
    }
}
