package network.srv_not_accepting;

import org.slf4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * produce read time out exception
 * packets can be captured using 'any' in wireshark
 */
public class Client {
    private static final Logger logger = getLogger(Client.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket();
        socket.setSoTimeout(10);
//        socket.bind(new InetSocketAddress("localhost", 8999));
//        socket.connect(new InetSocketAddress("93.184.216.34", 81), 100);
        socket.connect(new InetSocketAddress("localhost", 11999), 100);
        logger.info("connected");
        try {
            int ret = socket.getInputStream().read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(10000);

    }
}
