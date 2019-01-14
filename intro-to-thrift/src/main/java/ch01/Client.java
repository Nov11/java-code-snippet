package ch01;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Client {
    public static void main(String[] args) throws TException {
        TTransport tTransport = new TSocket("localhost", 12345);
        tTransport.open();
        TProtocol tProtocol = new TBinaryProtocol(tTransport);
        HelloSvc.Client client = new HelloSvc.Client(tProtocol);
        String ret = client.hello_func();
        System.out.println("RPC returns:" + ret);
    }
}
