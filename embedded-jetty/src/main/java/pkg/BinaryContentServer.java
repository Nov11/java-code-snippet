package pkg;

import org.eclipse.jetty.server.Server;
import pkg.handler.BinaryContentHandler;
import pkg.util.ServerUtil;

public class BinaryContentServer {
    private Server server = ServerUtil.makeServer(BinaryContentHandler.class);

    public BinaryContentServer() throws Exception {
        server.start();
    }

    public void join() throws InterruptedException {
        server.join();
    }

    public void stop() throws Exception {
        server.stop();
    }

    public void closeAndWait() throws Exception {
        stop();
        join();
    }

    public static void main(String[] args) throws Exception {
        BinaryContentServer server = new BinaryContentServer();
    }
}
