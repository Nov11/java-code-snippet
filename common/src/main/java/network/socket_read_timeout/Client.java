package network.socket_read_timeout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        Socket socket = socketChannel.socket();
        socket.setTcpNoDelay(true);
        socket.setSoTimeout(10);
        socketChannel.connect(new InetSocketAddress("localhost", 1777));
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[20]);
        logger.info("start reading");
        socketChannel.read(byteBuffer);
        logger.info("done");
        System.out.println(new String(byteBuffer.array(), StandardCharsets.UTF_8));
    }
}
