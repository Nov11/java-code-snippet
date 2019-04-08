package network.srv_not_accepting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    /**
     * not accepting intentionally
     *
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(60000);
        logger.info("socket created");
        while (true) {
            Thread.sleep(10000);
        }
    }
}
