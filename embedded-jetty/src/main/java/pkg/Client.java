package pkg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * read timeout does not abort connection
 */
public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 8090));
        socket.setSoTimeout(200);
        OutputStream outputStream = socket.getOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        bufferedOutputStream.write("1".getBytes());

        InputStream inputStream = socket.getInputStream();
        try {
            int ret = inputStream.read();
        } catch (Exception e) {
            logger.info("ex", e);
        }

        Thread.sleep(10 * 10000);

        logger.info("exit");
    }
}
