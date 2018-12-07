package baeldung.netty.client;

import baeldung.netty.common.Request;
import baeldung.netty.common.Response;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class HandlerTest {
    @Test
    public void childHandlerTest() {
        EmbeddedChannel channel = new EmbeddedChannel(new Handler());

        Request request = channel.readOutbound();
        Assert.assertEquals(request.getIntValue(), 11);
        Assert.assertEquals(request.getStringValue(), "s11");

        Response response = new Response();
        response.setReturnValue(22);

        Assert.assertFalse(channel.writeInbound(response));
        Assert.assertFalse(channel.isOpen());
    }
}