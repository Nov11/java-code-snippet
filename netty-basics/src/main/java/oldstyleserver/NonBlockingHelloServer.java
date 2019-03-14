package oldstyleserver;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NonBlockingHelloServer {
    public static void main(String[] args) throws IOException {
        NonBlockingHelloServer server = new NonBlockingHelloServer();
        server.server();
    }

    public void server() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8080));

        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            selector.select();

            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();

                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel client = serverSocketChannel1.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                } else if (selectionKey.isWritable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    socketChannel.write(ByteBuffer.wrap("hello".getBytes(CharsetUtil.UTF_8)));
                    socketChannel.close();
                    System.out.println("wrote 'hello'");
                }
            }
        }
    }
}
