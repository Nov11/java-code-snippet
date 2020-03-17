package tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WriteClosedClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 17000));
        socket.getOutputStream().write(1);
        socket.close();
        socket.getOutputStream().write(2);
    }
}
