package pkg;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.apache.thrift.TBase;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SerializeThriftList {
    static class SS<T extends TBase> extends Serializer<T>{
        Class<T> tClass;

        public SS(Class<T> tClass) {
            this.tClass = tClass;
        }

        @Override
        public void write(Kryo kryo, Output output, T object) {
            TSerializer tSerializer = new TSerializer();
            try {
                byte[] b = tSerializer.serialize(object);
                output.write(b);
            } catch (TException e) {
                e.printStackTrace();
            }
        }

        @Override
        public T read(Kryo kryo, Input input, Class<T> type) {
            try {
                byte[] b = new byte[input.available()];
                input.read(b);
                TDeserializer tDeserializer = new TDeserializer();
                T t = tClass.newInstance();
                tDeserializer.deserialize(t, b);
                return t;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (TException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
    public static void main(String[] args) {
        List<OneStruct> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            OneStruct oneStruct = new OneStruct();
            oneStruct.id = i;
            oneStruct.name = "name" + i;
            list.add(oneStruct);
        }


    }
}
