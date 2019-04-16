package echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.ReadTimeoutException;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ReadTimeOutHandler extends ChannelInboundHandlerAdapter {

    private final long readTimeout;
    private volatile ScheduledFuture<?> timeout;

    ReadTimeOutHandler(final long readTimeout) {
        this.readTimeout = readTimeout;
    }

    void scheduleTimeout(final ChannelHandlerContext ctx) {
        if (timeout == null) {
            timeout = ctx.executor().schedule(new ReadTimeoutTask(ctx), readTimeout, TimeUnit.MILLISECONDS);
        }
    }

    void removeTimeout(final ChannelHandlerContext ctx) {
        if (timeout != null) {
            timeout.cancel(false);
            timeout = null;
        }
    }

    private static final class ReadTimeoutTask implements Runnable {

        private final ChannelHandlerContext ctx;

        ReadTimeoutTask(final ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            if (ctx.channel().isOpen()) {
                try {
                    ctx.fireExceptionCaught(ReadTimeoutException.INSTANCE);
                    ctx.close();
                } catch (Throwable t) {
                    ctx.fireExceptionCaught(t);
                }
            }
        }
    }
}


