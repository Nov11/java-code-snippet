package some_test;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import pkg.MultiplicationService;

public class MultiplicationClient {
    public static void main(String[] args) throws TException {
        TTransport transport = new TSocket("localhost", 8090);
        transport.open();

        TProtocol protocol = new TBinaryProtocol(transport);
        MultiplicationService.Client client = new MultiplicationService.Client(protocol);

        perform(client);

        transport.close();
    }

    private static void perform(MultiplicationService.Client client) throws TException {
        int product = client.multiply(3, 5);
        System.out.println("3*5=" + product);
    }
}
