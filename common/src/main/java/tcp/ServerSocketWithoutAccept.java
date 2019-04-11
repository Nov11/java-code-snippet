package tcp;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * when limiting backlog, client can get a time out exception like this:
 * java.net.ConnectException: Operation timed out (Connection timed out)
 * I'm on the right track to reproduce redis Unreachable host exception with redisson client.
 *
 * connection on the client can succeed even without accept on server.
 */
public class ServerSocketWithoutAccept {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(17000, 2);
//        serverSocket.bind(new InetSocketAddress("localhost", 17000));
        Thread.sleep(1000000);
    }
}
