package pkg.bytebufs;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class RefCntFromUnpooled {
    public static void main(String[] args) {
        byte[] bytes = new byte[10];
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
        System.out.println(byteBuf.refCnt());
    }
}
