package http.bareclients;

import io.netty.channel.ChannelDuplexHandler;

import java.util.concurrent.CompletableFuture;

public class NaiveHandler extends ChannelDuplexHandler {
    private CompletableFuture<byte[]> responseContent = new CompletableFuture<>();
/**/
    public NaiveHandler() {
    }
}
