package pkg.actions;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import pkg.pojo.Address;
import pkg.pojo.Person;

import java.util.Date;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class ToAndFromPOJO {

    public static void main(String[] args) throws InterruptedException {
        CodecRegistry codecRegistry =
                fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                        fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry)
                .build();
        MongoClient mongoClient = MongoClients.create(mongoClientSettings);
        MongoCollection<Person> collection = mongoClient.getDatabase("testdb").getCollection("testc", Person.class);

//        Person person = new Person("name", 1, new Address("street", "city", "zip"));
//        person.setId("112");
//        person.setTimestamp(new Date());
//        collection.insertOne(person);
//        System.out.println(person);
//
//        {
//            Person p = collection.find(eq("_id", "112")).first();
//            System.out.println(p);
//        }
        {
            Person tmp = new Person("name", 1, new Address("street", "city", "zip"));
            tmp.setTimestamp(new Date());
            tmp.setId("7788");
            collection.replaceOne(eq("_id", "7788"), tmp, new ReplaceOptions().upsert(true));

            Person p = collection.find(eq("_id", "7788")).first();
            System.out.println(p);

            Thread.sleep(1000);
            tmp.setTimestamp(new Date());
            collection.replaceOne(eq("_id", "7788"), tmp, new ReplaceOptions().upsert(true));

        }
    }
}
