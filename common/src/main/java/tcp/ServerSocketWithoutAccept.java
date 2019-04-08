package tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

/**
 * connection on the client can succeed even without accept on server.
 */
public class ServerSocketWithoutAccept {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost", 17000));
        Thread.sleep(1000000);
    }
}
