package echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.ReadTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MongoDriverReadTimeOutHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MongoDriverReadTimeOutHandler.class);
    private final long readTimeout;
    private volatile ScheduledFuture<?> timeout;

    MongoDriverReadTimeOutHandler(final long readTimeout) {
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


