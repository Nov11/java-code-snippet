package pkg;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListOfTBaseSerializer<T extends TBase> {
    private static final Logger logger = LoggerFactory.getLogger(ListOfTBaseSerializer.class);
    private static final org.apache.thrift.protocol.TField FIELD_DESC = new org.apache.thrift.protocol.TField("HOLDER", org.apache.thrift.protocol.TType.LIST, (short) 1);
    private Class<T> tClass;

    public ListOfTBaseSerializer(Class<T> tClass) {
        this.tClass = tClass;
    }

    public void serialize(org.apache.thrift.protocol.TProtocol oprot, List<T> list) throws TException {
        if (list == null) {
            throw new IllegalArgumentException("null input list");
        }
        oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, list.size()));
        for (T _iter3 : list) {
            _iter3.write(oprot);
        }
        oprot.writeListEnd();
    }

    public byte[] serialize(List<T> list) throws TException {
        TMemoryBuffer tMemoryBuffer = new TMemoryBuffer(1000);
        TProtocol tProtocol = new TBinaryProtocol(tMemoryBuffer);
        serialize(tProtocol, list);
        return Arrays.copyOf(tMemoryBuffer.getArray(), tMemoryBuffer.length());
    }

    public List<T> deserialize(TProtocol iprot) throws TException, IllegalAccessException, InstantiationException {
        org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
        List<T> result = new ArrayList<>(_list0.size);
        T _elem1;
        for (int _i2 = 0; _i2 < _list0.size; ++_i2) {
            _elem1 = tClass.newInstance();
            _elem1.read(iprot);
            result.add(_elem1);
        }
        iprot.readListEnd();

        return result;
    }

    public List<T> deserialize(byte[] bytes) throws TException, IllegalAccessException, InstantiationException {
        TMemoryBuffer tMemoryBuffer = new TMemoryBuffer(1000);
        tMemoryBuffer.write(bytes);
        TBinaryProtocol tBinaryProtocol = new TBinaryProtocol(tMemoryBuffer);
        return deserialize(tBinaryProtocol);
    }
}
