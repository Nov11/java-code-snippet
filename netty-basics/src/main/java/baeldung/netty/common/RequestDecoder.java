package baeldung.netty.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class RequestDecoder extends ReplayingDecoder<Request> {
    private static final Logger logger = LoggerFactory.getLogger(RequestDecoder.class);
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        logger.info("decode");
        Request request = new Request();
        request.setIntValue(byteBuf.readInt());
        int sz = byteBuf.readInt();
        String s = byteBuf.readCharSequence(sz, StandardCharsets.UTF_8).toString();
        request.setStringValue(s);
        list.add(request);
    }
}
