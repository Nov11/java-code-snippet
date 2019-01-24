package jpkg;

import com.sun.xml.internal.ws.spi.db.FieldSetter;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryBuffer;

import java.lang.reflect.Field;

public class GetFieldsOfParent {
    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        TMemoryBuffer buffer = new TMemoryBuffer(2048);
        TMultiplexedProtocol tMultiplexedProtocol = new TMultiplexedProtocol(new TBinaryProtocol(buffer), "service_name");
        Class parent = tMultiplexedProtocol.getClass().getSuperclass();
        Field[] fields = parent.getDeclaredFields();

        for (Field f : fields) {
            System.out.println(f.getName());
        }

        fields[0].setAccessible(true);

        System.out.println("accessable : " + fields[0].isAccessible());

        TProtocol protocol = (TProtocol)
                fields[0].get(tMultiplexedProtocol);

        Field trans = protocol.getClass().getSuperclass().getDeclaredField("trans_");
        trans.setAccessible(true);

        TMemoryBuffer tMemoryBuffer = (TMemoryBuffer) trans.get(tMultiplexedProtocol);

        System.out.println(tMemoryBuffer == buffer);
        boolean ac = TMultiplexedProtocol.class.getSuperclass().getDeclaredFields()[0].isAccessible();
        System.out.println(ac);


        System.out.println(getMemoryBuffer(tMultiplexedProtocol) == buffer);
    }

    private static TMemoryBuffer getMemoryBuffer(TMultiplexedProtocol tMultiplexedProtocol) throws NoSuchFieldException, IllegalAccessException {
        Class tProtocolDecorator = tMultiplexedProtocol.getClass().getSuperclass();
        Field concreteProtocol = tProtocolDecorator.getDeclaredField("concreteProtocol");

        concreteProtocol.setAccessible(true);

        TBinaryProtocol tBinaryProtocol = (TBinaryProtocol) concreteProtocol.get(tMultiplexedProtocol);

        Class tProtocol = tBinaryProtocol.getClass().getSuperclass();
        Field trans = tProtocol.getDeclaredField("trans_");
        trans.setAccessible(true);

        return (TMemoryBuffer) trans.get(tMultiplexedProtocol);
    }
}
