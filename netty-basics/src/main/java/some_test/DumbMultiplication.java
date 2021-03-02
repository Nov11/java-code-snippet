//package some_test;
//
//import org.apache.thrift.server.TServer;
//import org.apache.thrift.server.TSimpleServer;
//import org.apache.thrift.transport.TServerSocket;
//import org.apache.thrift.transport.TServerTransport;
//import org.apache.thrift.transport.TTransportException;
//import pkg.MultiplicationService;
//
//public class DumbMultiplication {
//
//    public static void main(String[] mainArgs) throws TTransportException {
//        MultiplyServiceImpl handler = new MultiplyServiceImpl();
//        MultiplicationService.Processor<MultiplicationService.Iface> processor = new MultiplicationService.Processor<>(handler);
//
//        TServerTransport tServerTransport = new TServerSocket(8090);
//        TServer.Args args = new TServer.Args(tServerTransport).processor(processor);
//        TServer server = new TSimpleServer(args);
//
//        server.serve();
//    }
//}
