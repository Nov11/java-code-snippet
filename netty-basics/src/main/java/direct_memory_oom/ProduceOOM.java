package direct_memory_oom;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;

public class ProduceOOM {
    public static void main(String[] args) throws InterruptedException {
        UnpooledByteBufAllocator unpooledByteBufAllocator = new UnpooledByteBufAllocator(true);

        ByteBuf byteBuf = unpooledByteBufAllocator.buffer(60 * 1024 * 1024);

        System.out.println(Runtime.getRuntime().maxMemory());

        Thread.sleep(100000);
    }
}
