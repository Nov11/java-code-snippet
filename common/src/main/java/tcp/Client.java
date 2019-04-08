package tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 17000));
        System.out.println("exit");
    }
}
