package some_test;

import org.apache.thrift.TException;
import pkg.MultiplicationService;

public class MultiplyServiceImpl implements MultiplicationService.Iface {
    @Override
    public int multiply(int n1, int n2) throws TException {
        return n1 * n2;
    }
}
