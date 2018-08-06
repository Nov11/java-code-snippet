package serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.example.tutorial.AddressBookProtos;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * Created by c6s on 18-8-6
 */
public class ProtobufKryoTest {
    private Kryo kryo;
    private AddressBookProtos.AddressBook addressBook;
    private ProtobufKryo<AddressBookProtos.AddressBook> protobufKryo;
    private Output output;
    private Input input;

    @Before
    public void setUp() throws Exception {
        AddressBookProtos.Person.Builder builder = AddressBookProtos.Person.newBuilder();
        builder.setEmail("email");
        builder.setId(123456);
        builder.setName("name");
        builder.addPhones(AddressBookProtos.Person.PhoneNumber.newBuilder().setNumber("10086").setType(AddressBookProtos.Person.PhoneType.MOBILE).build());
        addressBook = AddressBookProtos.AddressBook.newBuilder()
                .addPeople(builder.build())
                .build();
        kryo = new Kryo();
        protobufKryo = new ProtobufKryo<>(AddressBookProtos.AddressBook.class);
        kryo.addDefaultSerializer(AddressBookProtos.AddressBook.class, protobufKryo);
        FileOutputStream fileOutputStream = new FileOutputStream(new File("out"));
        output = new Output(fileOutputStream);
        FileInputStream fileInputStream = new FileInputStream(new File("out"));
        input = new Input(fileInputStream);
    }

    @Test
    public void test() {
        kryo.writeClassAndObject(output, addressBook);
        output.close();

        AddressBookProtos.AddressBook addressBook1 = (AddressBookProtos.AddressBook) kryo.readClassAndObject(input);

        System.out.println("expected");
        System.out.println(addressBook);
        System.out.println("actual");
        System.out.println(addressBook1);
        Assert.assertEquals(addressBook1, addressBook);
    }
}
