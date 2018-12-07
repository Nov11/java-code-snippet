package baeldung.netty.server;

import baeldung.netty.common.Request;
import baeldung.netty.common.Response;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class HandlerTest {

    @Test
    public void serverHandler() {
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new Handler());

        Request request = new Request();
        request.setIntValue(1);
        request.setStringValue("2");
        Assert.assertFalse(channel.writeInbound(request));
        Response response = channel.readOutbound();
        Assert.assertEquals(2, response.getReturnValue());

    }
}