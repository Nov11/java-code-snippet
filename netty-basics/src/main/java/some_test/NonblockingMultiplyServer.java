package some_test;

import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;
import pkg.MultiplicationService;

public class NonblockingMultiplyServer {

    public static void main(String[] mainArgs) throws TTransportException {
        TNonblockingServerTransport tNonblockingServerTransport = new TNonblockingServerSocket(8090);
        TNonblockingServer.Args args = new TNonblockingServer.Args(tNonblockingServerTransport).processor(new MultiplicationService.Processor<>(new MultiplyServiceImpl()));
        TServer tServer = new TNonblockingServer(args);
        tServer.serve();
    }
}
