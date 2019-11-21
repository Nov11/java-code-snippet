package basic.lambda_iface;

import java.io.IOException;

public interface SomeIface<T> {
    T workImpl(String s) throws IOException;

    default T work(String s) {
        try {
            return workImpl(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
