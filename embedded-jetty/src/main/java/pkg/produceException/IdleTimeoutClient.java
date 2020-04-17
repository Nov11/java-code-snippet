package pkg.produceException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * connect but not sending anything
 */
public class IdleTimeoutClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 8090));


        OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
        outputStream.write("GET /ss HTTP/1.1\r\n".getBytes());
        outputStream.flush();
        outputStream.write("host:localhost\r\n".getBytes());
        Thread.sleep(10 * 1000);
        outputStream.write("\r\n".getBytes());

        outputStream.flush();

        socket.close();
    }
}
