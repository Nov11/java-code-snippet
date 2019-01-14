package baeldung.netty.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class ResponseDecoder extends ReplayingDecoder<Response> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Response response = new Response();
        response.setReturnValue(in.readInt());
        out.add(response);
    }
}
