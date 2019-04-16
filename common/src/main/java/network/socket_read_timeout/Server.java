package network.socket_read_timeout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(1777));
        String msg = "server";
        while (true) {
            SocketChannel socket = serverSocketChannel.accept();
            logger.info("accepted");
            Thread.sleep(10000);
            socket.write(ByteBuffer.wrap(msg.getBytes()));
            logger.info("write");
            socket.close();
        }

    }
}
