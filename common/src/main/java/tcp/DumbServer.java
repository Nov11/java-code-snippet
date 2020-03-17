package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class DumbServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(17000, 2);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        int a;
        while((a = inputStream.read()) != -1) {
            System.out.println(a);
        }
    }
}
