package pkg;

import org.apache.thrift.TException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ListOfTBaseSerializerTest {
    @Test
    public void test() throws TException, InstantiationException, IllegalAccessException {
        List<OneStruct> param = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            param.add(new OneStruct().setName(String.valueOf(i)).setId(i));
        }
        ListOfTBaseSerializer<OneStruct> serializer = new ListOfTBaseSerializer<>(OneStruct.class);
        byte[] b = serializer.serialize(param);


        List<OneStruct> ret = serializer.deserialize(b);


        Assert.assertEquals(param, ret);
    }
}