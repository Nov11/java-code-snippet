package http.bareclients;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Runner {
    private static final Logger logger = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        BareClient client = new BareClient();

        NaiveConnection connection = client.makeConnection();

        DefaultFullHttpRequest req = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/ss");
        HttpHeaders headers = req.headers();
        headers.add("host", "localhost:8090");

        connection.connect("localhost", 8090).sync();

        CompletableFuture<byte[]> content = connection.request(req);

        logger.info("{}", content.get());
    }
}
