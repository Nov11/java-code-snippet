package http.bareclients;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class NaiveConnectionTest {
    private static final Logger logger = LoggerFactory.getLogger(NaiveConnectionTest.class);

    @Test
    public void getResponse() throws InterruptedException, ExecutionException {
        BareClient client = new BareClient();

        NaiveConnection connection = client.makeConnection();

        FullHttpRequest req = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/ss");

        connection.connect("localhost", 8080).sync();

        CompletableFuture<byte[]> content = connection.request(req);

        logger.info("{}", content.get());
    }
}