package oldstyleserver;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockingHelloServer {
    public static void main(String[] args) throws IOException {
        BlockingHelloServer server = new BlockingHelloServer();
        server.server();
    }

    public void server() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket socket = serverSocket.accept();
        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("received connection");
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write("hello".getBytes(CharsetUtil.UTF_8));
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("response has bean written out");
            }
        }.start();
    }
}
