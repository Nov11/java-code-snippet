package pkg.v_core;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpServer {
    private static final Logger logger = LoggerFactory.getLogger(TcpServer.class);

    /**
     * ugly synchronization
     *
     * @throws InterruptedException
     */
    @Test
    public void name() throws InterruptedException {
        Vertx vertx = Vertx.vertx();
        NetServerOptions netServerOptions = new NetServerOptions().setPort(1234);
        NetServer server = vertx.createNetServer(netServerOptions);
        server.connectHandler(socket -> {
                    socket.handler(b -> {
                        logger.info("bytes received : {}", b.length());
                    });
                }
        ).listen(p -> {
            NetClient netClient = vertx.createNetClient();
            netClient.connect(1234, "localhost", ar -> {
                if (ar.succeeded()) {
                    NetSocket socket = ar.result();
                    socket.write("msg", h -> {
                        socket.close();
                    });
                }
            });
        });
        Thread.sleep(2 * 1000);
    }
}
