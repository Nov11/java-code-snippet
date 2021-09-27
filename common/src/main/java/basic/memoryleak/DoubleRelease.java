package basic.memoryleak;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class DoubleRelease {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.release();
        byteBuf.release();
    }
}
