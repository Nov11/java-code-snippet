package pkg.actions;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import pkg.pojo.Address;
import pkg.pojo.Person;

import java.util.Date;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class ToAndFromPOJO {

    public static void main(String[] args) {
        CodecRegistry codecRegistry =
                fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                        fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry)
                .build();
        MongoClient mongoClient = MongoClients.create(mongoClientSettings);
        MongoCollection<Person> collection = mongoClient.getDatabase("testdb").getCollection("testc", Person.class);

        Person person = new Person("name", 1, new Address("street", "city", "zip"));
        person.setTimestamp(new Date());
        collection.insertOne(person);

        System.out.println(person);
    }
}
