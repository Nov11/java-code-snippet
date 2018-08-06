package serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.GeneratedMessageV3;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by c6s on 18-8-6
 */
public class ProtobufKryo<P extends GeneratedMessageV3> extends Serializer<P>{
    private final Method parser;

    ProtobufKryo(Class<P> theClass) {
        try {
            parser = theClass.getDeclaredMethod("parseFrom", InputStream.class);
            parser.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(theClass.toString() + " doesn't have parser");
        }
    }
    @Override
    public void write(Kryo kryo, Output output, P generatedMessage) {
        try {
            generatedMessage.writeTo(output);
        } catch (IOException e) {
            // This isn't supposed to happen with a Kryo output.
            throw new RuntimeException(e);
        }
    }

    @Override
    public P read(Kryo kryo, Input input, Class<P> gmClass) {
        try {
            return (P)parser.invoke(null, input);
        } catch (InvocationTargetException | IllegalAccessException e) {
            // These really shouldn't happen
            throw new IllegalArgumentException(e);
        }
    }
}
