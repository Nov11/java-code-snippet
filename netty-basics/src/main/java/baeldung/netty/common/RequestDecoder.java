package baeldung.netty.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class RequestDecoder extends ReplayingDecoder<Request> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Request request = new Request();
        request.setIntValue(byteBuf.readInt());
        int sz = byteBuf.readInt();
        String s = byteBuf.readCharSequence(sz, StandardCharsets.UTF_8).toString();
        request.setStringValue(s);
        list.add(request);
    }
}
