package ch01;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

public class Server {
    static class Handler implements HelloSvc.Iface {

        @Override
        public String hello_func() throws TException {
            return "ret_value";
        }
    }

    public static void main(String[] args) throws TTransportException {
        TServerTransport tServerTransport = new TServerSocket(12345);
        Handler handler = new Handler();
        HelloSvc.Processor<HelloSvc.Iface> processor = new HelloSvc.Processor<>(handler);

        TServer.Args args1 = new TServer.Args(tServerTransport).processor(processor);
        TServer server = new TSimpleServer(args1);
        server.serve();
    }
}
