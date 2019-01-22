package pkg;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryBuffer;

public class TestDrive {
    public static void main(String[] args) throws TException {
        Account account = new Account();
        account.withdraw(5);

        TProtocol tProtocol = new TBinaryProtocol(new TMemoryBuffer(100));
        OneStruct one = new OneStruct();


        System.out.println("write to binary protocol");
        one.write(tProtocol);

        System.out.println("write to json protocol");
        TProtocol jsonProtocol = new TJSONProtocol(new TMemoryBuffer(100));
        one.write(jsonProtocol);
    }
}
